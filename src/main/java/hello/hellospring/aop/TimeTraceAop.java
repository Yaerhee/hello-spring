package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component //SpringConfig에 @Bean으로 등록해서 활용해도 됨
@Aspect //AOP로 활용하기 위한 선언
public class TimeTraceAop {

    @Around("execution(* hello.hellospring..*(..))") //공통 관심사를 타게팅
    //hellospring 하위의 class에는 일괄 적용하게 설정함
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("START: " + joinPoint.toString());
        try {
            return joinPoint.proceed(); //Inline 처리(result)
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END: " + joinPoint.toString() + " " + timeMs + "ms");
        }

    }
}
