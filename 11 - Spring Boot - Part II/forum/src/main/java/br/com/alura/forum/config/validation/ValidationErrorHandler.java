package br.com.alura.forum.config.validation;

import br.com.alura.forum.dto.ErroDeFormularioDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

// ! Funciona como Interceptor
// ? Esse é utilizado para tratar/gerenciar
// ? todos os erros da aplicação
// ? e simplificar

@RestControllerAdvice
public class ValidationErrorHandler {

    // * Internacionalização das mensagens feita pelo Spring
    @Autowired
    private MessageSource messageSource;

    // ? Gerencia todos os erros relacionados
    // ? ao Bean Validation

    // ? Necessário o ResponseStatus
    // ? pois por padrão o String devolveria
    // ? 200, assumindo que o erro está tratado
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErroDeFormularioDto> handle(MethodArgumentNotValidException exception) {

        List<ErroDeFormularioDto> dto = new ArrayList<>();

        // * Pegando todos os erros de formulário
        List<FieldError> errors = exception.getBindingResult().getFieldErrors();

        errors.forEach(e -> {
            String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            ErroDeFormularioDto erro = new ErroDeFormularioDto(e.getField(), mensagem);

            dto.add(erro);
        });

        return dto;
    }
}
