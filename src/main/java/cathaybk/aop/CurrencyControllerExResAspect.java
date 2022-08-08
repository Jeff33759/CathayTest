package cathaybk.aop;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import cathaybk.controller.CurrencyController;
import cathaybk.exception.CyApiException;
import cathaybk.exception.MyWriteDBException;
import cathaybk.model.dto.send.ActionFailedRes;
import cathaybk.serviceimpl.CurrencyServiceImpl;

/**
 * 攔截控制器的例外，根據例外處理回應的格式。
 * */
@RestControllerAdvice(basePackageClasses = {CurrencyController.class})
public class CurrencyControllerExResAspect {
	
	private static final Logger log = 
			LoggerFactory.getLogger(CurrencyControllerExResAspect.class);
	
	/**
	 * 攔截有關於參數對接的型別驗證及@Valid驗證時發生的例外。
	 * */
    @ExceptionHandler(value = BindException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ActionFailedRes handleDataDockingException(BindException ex, HttpServletRequest request) {
    	FieldError fieldError = ex.getFieldError();
    	String errCause = fieldError.getCode();
    	ActionFailedRes res = new ActionFailedRes();
    	res.setPath(request.getRequestURI());
    	res.setMethod(request.getMethod());
    	if(errCause.equals("typeMismatch")) {
//    		當參數型別匹配錯誤
    		res.setMsg(String.format("The type of query parameter '%s' is wrong. "
    				+ "Consider changing it to boolean or numeric.", fieldError.getField()));
    	}else {
//    		來自DTO裡面欄位驗證的message
    		res.setMsg(fieldError.getDefaultMessage());
    	}
        return res;
    }
    
    
    /**
     * 攔截有關於{@link @RequestBody}參數對接時，客戶端使用了錯誤的JSON格式時發生的例外。
     * */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ActionFailedRes handleDataDockingException2(HttpMessageNotReadableException ex, 
    		HttpServletRequest request) {
    	ActionFailedRes res = new ActionFailedRes();
    	res.setPath(request.getRequestURI());
    	res.setMethod(request.getMethod());
    	res.setMsg("The error occurred when parsing JSON in book server, "
    			+ "please check if your JSON format are as expected.");
    	return res;
    }
    
    
	/**
	 * 攔截有關於{@link @RequestBody}參數對接，使用@Validated驗證時發生的例外。</p>
	 * 客戶端的JSON格式對，但輸入了我們不想被輸入的值。</p>
	 * 捕捉所有沒驗證過的message(可能有複數)，組成錯誤訊息模板。
	 * */
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ActionFailedRes handleDataDockingException3(ConstraintViolationException ex, 
    		HttpServletRequest request) {
    	String errMsgTemplate = "The path variable validation failed. ";
    	Set<ConstraintViolation<?>> set = ex.getConstraintViolations();
    	for(ConstraintViolation<?> v : set) {
    		errMsgTemplate += v.getMessage();
    	}
    	ActionFailedRes res = new ActionFailedRes();
    	res.setPath(request.getRequestURI());
    	res.setMethod(request.getMethod());
    	res.setMsg(errMsgTemplate);
        return res;
    }
    
    /**
     * 攔截有關於將資料寫入DB時發生的例外，
     * 基本上都是參數錯誤造成(例如ID不存在或重複)，所以回400。
     * 
     * @see CurrencyServiceImpl
     * */
    @ExceptionHandler(value = MyWriteDBException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ActionFailedRes handleWriteDataToDBException(MyWriteDBException me, 
    		HttpServletRequest request) {
    	ActionFailedRes res = new ActionFailedRes();
    	res.setPath(request.getRequestURI());
    	res.setMethod(request.getMethod());
    	res.setMsg(me.getMessage());
    	return res;
    }
    
    
    /**
     * 攔截向coindeskApi取資料時發生的例外。</p>
     * 因為{@link CurrencyController #readCurrency()}的服務強依賴於該API，
     * 所以當該API沒有提供服務，那readCurrency就不提供服務，回應503。
     * */
    @ExceptionHandler(value = CyApiException.class)
    @ResponseStatus(code = HttpStatus.SERVICE_UNAVAILABLE)
    public ActionFailedRes handleGetDataFromcoindeskApiException(CyApiException cae, 
    		HttpServletRequest request) {
    	ActionFailedRes res = new ActionFailedRes();
    	res.setPath(request.getRequestURI());
    	res.setMethod(request.getMethod());
    	res.setMsg(cae.getMessage());
    	return res;
    }

    
    /**
     * 系統發生了其他無法預期的例外。
     * */
    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ActionFailedRes handleException(
    		Exception e, HttpServletRequest req,
    			HttpServletResponse res) {
    	log.error(e.getMessage());
    	ActionFailedRes resObj = new ActionFailedRes();
    	resObj.setMethod(req.getMethod());
    	resObj.setPath(req.getRequestURI());
    	resObj.setMsg("An unexpected error occurs in the server, "
    			+ "please contact the webmaster.");
    	return resObj;
    }

}
