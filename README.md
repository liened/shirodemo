# shirodemo http://jinnianshilongnian.iteye.com/blog/2018398
1.通过subject的login登录，如果登录失败将抛出相应的AuthenticationException，
    如果登录成功调用isAuthenticated就会返回true，即已经通过身份验证；
    如果isRemembered返回true，表示是通过记住我功能登录的而不是调用login方法登录的。
    isAuthenticated/isRemembered是互斥的，即如果其中一个返回true，另一个返回false。
    
2.hasRole*进行角色验证，验证后返回true/false；而checkRole*验证失败时抛出AuthorizationException异常。
 
3.isPermitted*进行权限验证，验证后返回true/false；而checkPermission*验证失败时抛出AuthorizationException。

4.会话
  Session getSession(); //相当于getSession(true)  
  Session getSession(boolean create);    
  类似于Web中的会话。如果登录成功就相当于建立了会话，接着可以使用getSession获取；
    如果create=false如果没有会话将返回null，而create=true如果没有会话会强制创建一个。

5.RunAs:
RunAs即实现“允许A假设为B身份进行访问”；
    通过调用subject.runAs(b)进行访问；接着调用subject.getPrincipals将获取到B的身份；此时调用isRunAs将返回true；而a的身份需要通过subject. getPreviousPrincipals获取；
    如果不需要RunAs了调用subject. releaseRunAs即可。

6.多线程
    <V> V execute(Callable<V> callable) throws ExecutionException;  
    void execute(Runnable runnable);  
    <V> Callable<V> associateWith(Callable<V> callable);  
    Runnable associateWith(Runnable runnable);   
    实现线程之间的Subject传播，因为Subject是线程绑定的；因此在多线程执行中需要传播到相应的线程才能获取到相应的Subject。最简单的办法就是通过execute(runnable/callable实例)直接调用；或者通过associateWith(runnable/callable实例)得到一个包装后的实例；
    它们都是通过：1、把当前线程的Subject绑定过去；2、在线程执行结束后自动释放。

总结：
对于Subject我们一般这么使用：
1、身份验证（login）
2、授权（hasRole*/isPermitted*或checkRole*/checkPermission*）
3、将相应的数据存储到会话（Session）
4、切换身份（RunAs）/多线程身份传播
5、退出