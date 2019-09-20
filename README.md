# aspectLearning
spring boot 面向切面编程

### AOP
将通用的逻辑从业务逻辑中分离出来！

AOP代码好些，主要是要找准要切的地方。AOP的理论知识需要多次项目实战配合理解。

### aspect
1. 切入点（Pointcut）在哪些类，哪些方法上切入（where）
2. 通知（Advice）在方法执行的什么实际（when:方法前/方法后/方法前后）做什么（what:增强的功能）
3. 切面（Aspect）切面 = 切入点 + 通知，通俗点就是：在什么时机，什么地方，做什么增强！
4. 织入（Weaving）把切面加入到对象，并创建出代理对象的过程。（由 Spring 来完成）

### 注解
1. @Before	前置通知，在连接点方法前调用
2. @Around	环绕通知，它将覆盖原有方法，但是允许你通过反射调用原有方法，后面会讲
3. @After	后置通知，在连接点方法后调用
4. @AfterReturning	返回通知，在连接点方法执行并正常返回后调用，要求连接点方法在执行过程中没有发生异常
5. @AfterThrowing	异常通知，当连接点方法异常时调用

### execution
execution 是编写切面类的匹配规则，在正式开发过程中，业务要更加复杂。从网络找到些execution仅供参考。[02-01 AOP学习之execution](https://www.jianshu.com/p/509fcd44f76e)
1. execution():：表达式主体。
2. 第一个*号：表示返回类型， *号表示所有的类型。
3. 包名：表示需要拦截的包名，后面的两个句点表示当前包和当前包的所有子包，com.sample.service包、子孙包下所有类的方法。
4. 第二个*号：表示类名，*号表示所有的类。
5. *(..)：最后这个星号表示方法名，*号表示所有的方法，后面括弧里面表示方法的参数，两个句点表示任何参数

模式 | 描述  
-|-
public * *(..)	 | 任何公共方法的执行 |
* com.learn..IHelloService.*() | com.learn包及所有子包下IHelloService接口中的任何无参方法 |
* com.learn..*.*(..)	 | com.learn包及所有子包下任何类的任何方法 |
* com.learn..IHelloService.*(*)	| com.learn包及所有子包下IHelloService接口的任何只有一个参数方法
* (!com.learn..IHelloService+).*(..)	| 非“com.learn包及所有子包下IHelloService接口及子类型”的任何方法
* com.learn..IHelloService+.*()	| com.learn包及所有子包下IHelloService接口及子类型的的任何无参方法
* com.learn..IHelloService*.test*(java.util.Date)	| com.learn包及所有子包下IHelloService前缀类型的的以test开头的只有一个参数类型为java.util.Date的方法，注意该匹配是根据方法签名的参数类型进行匹配的，而不是根据执行时传入的参数类型决定的，如定义方法：public void test(Object obj);即使执行时传入java.util.Date，也不会匹配的；
* com.learn..IHelloService.test(..) throws IllegalArgumentException, ArrayIndexOutOfBoundsException	| com.learn包及所有子包下IHelloService前缀类型的的任何方法，且抛出IllegalArgumentException和ArrayIndexOutOfBoundsException异常
* (com.learn..IHelloService+ && java.io.Serializable+).*(..)	| 任何实现了com.learn包及所有子包下 IHelloService接口和java.io.Serializable接口的类型的任何方法
@java.lang.Deprecated * *(..)	| 任何持有@java.lang.Deprecated注解的方法
(@com.learn..Secure *) *(..)	| 任何返回值类型持有@com.learn..Secure的方法
* (@com.learn..Secure *).*(..)	| 任何定义方法的类型持有@com.learn..Secure的方法
* *(@com.learn..Secure (*) , @com.learn..Secure (*))	| 任何签名带有两个参数的方法，且这个两个参数都被@Secure标记了，如public void test(@Secure String str1, @Secure String str1)
* *((@ com.learn..Secure *))或 * *(@ com.learn..Secure *)	| 任何带有一个参数的方法，且该参数类型持有@com.learn..Secure；如public void test(Model model);且Model类上持有@Secure注解
* *( @com.learn..Secure (@com.learn..Secure *) , @ com.learn..Secure (@com.learn..Secure *))	| 任何带有两个参数的方法，且这两个参数都被@com.learn..Secure标记了；且这两个参数的类型上都持有@ com.learn..Secure；
* *( java.util.Map<com.learn..Model, com.learn..Model> , ..)	| 任何带有一个java.util.Map参数的方法，且该参数类型是以< com.learn..Model, com.learn..Model >为泛型参数；注意只匹配第一个参数为java.util.Map,不包括子类型；如public void test(HashMap<Model, Model> map, String str);将不匹配，必须使用“* *( java.util.HashMap<com.learn..Model,com.learn..Model> , ..)”进行匹配； 而public void test(Map map, int i);也将不匹配，因为泛型参数不匹配 * *(java.util.Collection<@com.learn..Secure *>) | 任何带有一个参数（类型为java.util.Collection）的方法，且该参数类型是有一个泛型参数，该泛型参数类型上持有@com.learn..Secure注解； 如public void test(Collection<Model> collection);Model类型上持有@com.learn..Secure * *(java.util.Set<? extends HashMap>) 任何带有一个参数的方法，且传入的参数类型是有一个泛型参数，该泛型参数类型继承与HashMap； Spring AOP目前测试不能正常工作
* *(java.util.List<? super HashMap>) | 	任何带有一个参数的方法，且传入的参数类型是有一个泛型参数，该泛型参数类型是HashMap的基类型；如public voi test(Map map)；Spring AOP目前测试不能正常工作
* *(*<@com.learn..Secure *>)	| 任何带有一个参数的方法，且该参数类型是有一个泛型参数，该泛型参数类型上持有@com.learn..Secure注解；Spring AOP目前测试不能正常工作
