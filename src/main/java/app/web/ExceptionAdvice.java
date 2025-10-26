package app.web;


import app.exception.NotificationServiceFeignCallException;
import app.exception.UsernameAlreadyExistException;
import app.user.model.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.rmi.AccessException;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(UsernameAlreadyExistException.class)
    public String handleUsernameAlreadyExistException(HttpServletRequest request, RedirectAttributes redirectAttributes, UsernameAlreadyExistException exception) {
        String message = exception.getMessage();
        redirectAttributes.addFlashAttribute("usernameAlreadyExistMessage", message);
        return "redirect:/register";
    }

    @ExceptionHandler(NotificationServiceFeignCallException.class)
    public String handleNotificationServiceFeignCallException(RedirectAttributes redirectAttributes, NotificationServiceFeignCallException exception) {
        String message = exception.getMessage();
        redirectAttributes.addFlashAttribute("clearHistoryErrorMessage", message);
        return "redirect:/notification";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({
            AccessException.class,
            NoResourceFoundException.class,
            MethodArgumentTypeMismatchException.class,
            MissingRequestValueException.class
    })
    public ModelAndView handleNotFoundExceptions(Exception exception) {
        return new ModelAndView("not-found");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ModelAndView handleAnyException(Exception exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("internal-server-error");
        modelAndView.addObject("errorMessage", exception.getClass().getSimpleName());
        return modelAndView;
    }

}
