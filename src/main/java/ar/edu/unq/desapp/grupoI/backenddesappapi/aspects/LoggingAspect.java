package ar.edu.unq.desapp.grupoI.backenddesappapi.aspects;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.json.JSONParser;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import springfox.documentation.spring.web.json.Json;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class LoggingAspect
{
    private static final Logger LOGGER = LogManager.getLogger(LoggingAspect.class);

    //AOP expression for which methods shall be intercepted
    @Around("execution(* ar.edu.unq.desapp.grupoI.backenddesappapi.webservices..*(..)))")
    public Object profileAllMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable
    {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

        //Get intercepted method details
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();
        List<Object> parameters = Arrays.asList(proceedingJoinPoint.getArgs());
        List<String> paramsToPrint = new ArrayList<>();


        parameters.forEach(p -> {
            Gson gson = new Gson();
            paramsToPrint.add(gson.toJson(p));
        });

        final StopWatch stopWatch = new StopWatch();

        //Measure method execution time
        stopWatch.start();
        Object result = proceedingJoinPoint.proceed();
        stopWatch.stop();

        //Log method execution time
        LOGGER.info("Execution time of " + className + "." + methodName + " :: " + stopWatch.getTotalTimeMillis() + " ms." + " Parameters: " + paramsToPrint);


        return result;
    }


}
