package zy.code.project_highconcurrencymall.exception;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import zy.code.project_highconcurrencymall.result.CodeMsg;
import zy.code.project_highconcurrencymall.result.Result;

/**
 * 全局的异常捕获
 */

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
	@ExceptionHandler(value=Exception.class)
	public Result<String> exceptionHandler(HttpServletRequest request, Exception e){
		//打印异常
		e.printStackTrace();
		if(e instanceof GlobalException) {

			//可以转换为自定义异常类，并返回错误码
			GlobalException ex = (GlobalException)e;
			return Result.error(ex.getCm());
		}else if(e instanceof BindException) {
			//参数校验异常
			//获取绑定异常
			BindException ex = (BindException)e;
			List<ObjectError> errors = ex.getAllErrors();
			ObjectError error = errors.get(0);
			//获取错误的异常信息
			String msg = error.getDefaultMessage();
			//将异常信息携带到返回结果种
			return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));
		}else {
			//普通服务端出错异常
			return Result.error(CodeMsg.SERVER_ERROR);
		}
	}
}
