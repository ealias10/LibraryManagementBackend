package com.example.LibraryManagementSystem.interceptor;



import com.example.LibraryManagementSystem.exception.LibraryManagementSystemException;
import com.example.LibraryManagementSystem.utils.Constants;
import com.example.LibraryManagementSystem.vo.ErrorVO;
import com.example.LibraryManagementSystem.vo.ResponseVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  public static byte[] getUnauthorizedResponseForAuthFilterErrors(Exception exception) {
    ErrorVO errorVO = new ErrorVO();
    errorVO.getErrors().add(exception.getMessage());
    if (exception instanceof LibraryManagementSystemException) {
      errorVO.setErrorCode(((LibraryManagementSystemException) exception).getErrorCode());
    } else {
      errorVO.setErrorCode(Constants.UNAUTHORIZED_ERROR_CODE);
    }
    ResponseVO<Object> responseVO = new ResponseVO<>();
    responseVO.setStatus(HttpStatus.UNAUTHORIZED.value());
    responseVO.setMessage(HttpStatus.UNAUTHORIZED.getReasonPhrase());
    responseVO.setError(errorVO);
    String responseString = convertToJsonString(responseVO);
    return responseString.getBytes();
  }

  private static String convertToJsonString(ResponseVO<Object> response) {
    String responseString = null;
    try {
      responseString = OBJECT_MAPPER.writeValueAsString(response);
    } catch (JsonProcessingException e) {
      responseString = response.toString();
    }
    return responseString;
  }

  @ExceptionHandler(LibraryManagementSystemException.class)
  public ResponseEntity<ResponseVO<Object>> handle(
      HttpServletRequest request, LibraryManagementSystemException exception) {
    logError(request, exception);
    ErrorVO errorVO = new ErrorVO();
    errorVO.getErrors().add(exception.getMessage());
    errorVO.setErrorCode(exception.getErrorCode());
    if (exception.getDisplayTitle() != null) {
      errorVO.setDisplayTitle(exception.getDisplayTitle());
    }
    if (exception.getDisplayMessage() != null) {
      errorVO.setDisplayMessage(exception.getDisplayMessage());
    }
    ResponseVO<Object> responseVO = new ResponseVO<>();
    responseVO.setStatus(exception.getStatus().value());
    responseVO.setMessage(exception.getStatus().getReasonPhrase());
    responseVO.setError(errorVO);

    return new ResponseEntity<>(responseVO, exception.getStatus());
  }

  private void logError(HttpServletRequest request, Exception exception) {

    log.error("Failed {} {} {}", request.getMethod(), request.getRequestURI(), exception);
  }
}
