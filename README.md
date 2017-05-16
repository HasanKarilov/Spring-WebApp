Java Spring
===============================
    Когда вы слышите что-то, вы забываете это.
    Когда вы видите что-то, вы запоминаете это.
    Но только когда вы начинаете делать это,
    вы начинаете понимать это

    Старинная китайская поговорка

Контекс веб приложения и контекст Spring
===============
context.xml - для всех абсолютно веб компонентов которые находяться внутри вашего приложения. 

mvc-dispatcher-servlet.xml - это контекст именно для DispatcherServlet'а (DS). Спец. настройочный файл для того что бы DS с него считовал данные. Поэтому здесь желательно ничего не изменять. Все новые бины мы должны обьявлять в context.xml.
InternalResourceViewResolver - ищет представлении с префиксом /WEB-INF/pages и суффиксом .jsp.

web.xml - все веб приложения начинаются с этого файла. В этом файла во первых описовается сам DispatcherServlet (DS), что бы все запросы которые приходят в Spring контейнер проходили через этот сервлет (DS) и им управлялись. В <init-param> мы указываем конфигурационный файл mvc-dispatcher-servlet.xml что бы DS считовал именно его. В web.xml мы соединяем веб контейнер и Spring контейнер, это делается с помощью ContextLoaderListener.

Передача параметров формы
===============

String метод в контроллере просто возвращяет название страницы.
обьект ModelAndView -  может задать model и view для метода контроллера.

JSP - страница

    <form:form method = "post" commandName="user" action="showinfor">
     <form:label path="name">User</form:label>
        <form:input path="name"  tabindex="1" placeholder="user name" required="please"/>
        <form:input path="password" type="password" tabindex="2"  placeholder="user password" required="please" />
    </form:form>
    
Класс USER
   
    public class User {
        private String name;
        private String password;
    
        public String getName() {
            return name;
        }
    
        public void setName(String name) {
            this.name = name;
        }
    
        public String getPassword() {
            return password;
        }
    
        public void setPassword(String password) {
            this.password = password;
        }
    }

path="name" берет данные через getter&setter'ы класса User т.е. он имеет доступ к переменным класса User через их getter&setters. 

    @RequestMapping(value="/", method = RequestMethod.GET)
	public ModelAndView printWelcome() {

		return new ModelAndView("registrform", "user", new User());
	}

	@RequestMapping(value = "/showinfor", method = RequestMethod.POST)
	public ModelAndView showInfor(@ModelAttribute("user") User user){
		// view name, model name, object
		return new ModelAndView("showinfor", "user", user);
	}
	
Получется когда пользователь введет данные форму, эти данные запишутся в обьект User  в модель "user" и эта модель записовается в общий контекст вашего приложеня Spring MVC. Модель "user" будет доступна из всех методов допустив представлениях (JSP). И эти значения формы передадутся в "showinfor" (@RequestMapping(value="showinfor", POST)).

Мы модель "user" в методе "showinfor" получаем с помощью специальной аннотации  @ModelAttribute("user") и записоваем ее значение в User user.

В JSP странице showinfor данные берутся из модели под названием "user"
    
    <p>Ваш логин: ${user.name}</p>
    <p>Ваш пароль: ${user.password}</p>
    

WebApplicationContext
===============

Когда в DispatcherServlet приходит запрос, он сразу работает с WebApplicationContext. Внутри этого контекста храняться разные реализации интерфейсов т.е. храняться разные обьекты. Есть интерфейс HandlerMapping, ViewResolver Controllers. 
ViewResolver - используется для того что бы определить какое именно 
представление использовать для отоброжения данных.
HandlerMapping - определяет какой именно запрос туда отправиться.
Далее WebApplicationContext определяет какой именно контроллер будет использоваться для этого. Все это храниться в WebApplicationContext.

ContextLoaderListener - это как раз таки и есть инициализация экземпляра интерфейса WebApplicationContext. По умолчанию используется реализация XmlWebApplicationContext. Именно ContextLoaderListener загружает ваш экземпляр WebApplicationContext.

web.xml - с него начинается веб приложение, с него считоваются первые параметры, с него считоваются как именно запросы будут попадать в сервлеты.
Получение доступа к контексту происходит в web.xml.


HandlerMapping и ViewResolver
===============

DispatcherServlet с помощью HandlerMapping'а определяет какой именно контроллер ему использовать т.е. куда именно ему перенаправить запрос. Дале DS он перенаправляет запрос к нужнуму контроллеру. Как только контроллер отработает, он получает задание найти нужное представление. За этим он обращяется к ViewResolver.

URL handler mapping - по умолчанию в Spring используется  RequestMappingHandlerMapping, он позволяет использовать в контроллерах аннотации которые мы обрабатоваем в их методах и возвращять разные обьекты. У HandlerMapping есть разные реализации которые обрабатовают запросы пользователя по разному

Поняти "internal resource view"
View Resolver - по умолчанию используется InternalResourceViewResolver. ViewResolver указан в DS 

     <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
            <property name="prefix" value="/WEB-INF/pages/"/>
            <property name="suffix" value=".jsp"/>
    </bean>

Префикс до, а суффик он после и название представления мы передаем в контроллере. Например если мы в контроллере передаем представление "index" то он получит такой вид /WEB-INF/pages/index.jsp.

Папка WEB-INF - это специальная папка которая не имет прямого доступа с помощью адрессной строки. Если вы в браузере полнуть путь к jsp странице то ничего не найдете т.к. эта папка скрыта, следовательно и все файлы в нем скрыты. Для ограничение прямого доступа к файлам рекомендуется добавлять файлы в папку WEB-INF.

Interceptor (перехватчик)
===============
Interceptor - это спец механизм который позволяет перехватовать запросы допустим до того как они попадут в контроллер и тем самым делать с ним какие то манипуляции. И наоборот когда запрос попадает в контроллер и происходит обработка, после вы можете изменить результат обработки и на основе их делать какие то проверки. По сути это спец механизм который позволяет вам работать с запросами клиента.

**Механизм работы**

Клиент делает запрос, этот запрос попадает в цепочку Interceptor'ов (вы можете стоить не один interceptor а несколько). Interceptor(перехватчик) - он перехватывает запросы и делает с ними какие то манипуляции. Далее выполняется контроллер, если вы выбрали interceptor до обработки контроллера (есть несколько вариантов interceptor'а). Затем контроллер выполяет свои действия, затем идет поиск представления и далее представление отправляется клиенту. Т.е. запрос клиента прежде чем попасть в контроллер может попасть в цепочку Interceptor'ов.

**Методы интерфейса HandlerInterceptor**
_boolean preHandle(HttpServletRequest, HttpServletResponse, Object handler)_ - обработка до того как запрос попадет в контроллер. Если метод вернет false дальнейщяя обработка прекращяется, если true то дальнейщяя обработка будет дальше идти по цепочке.

_void postHandle(HttpServletRequest, HttpServletResponse, Object handler, ModelAndView)_ - обработка после того handler обработал, в данном случае handler это контроллер обработал все данные, записал модели и представления. После этого идет перехват запроса но до его отображения.

_void afterCompletion(HttpServletRequest, HttpServletResponse, Object handler, Exception)_ - это когда отображение уже было найдено. Т.е. полный цикл уже обработал в этом случае тоже можно отловить конечную часть т.е. все что произашло в обработке запроса (данные,представления).

**Отличие от Filter**
- Возможность работы напрямую с обьектами (моделями).
- Доступ к контроллерам.

Предназначение Inteceptor и Filter одно и тоже. Но фильтры подходят для стандартных обьектов Request и Response если ничего лишнего кроме них не нужно. Interceptor более близок для работы со Spring.

Обьявление Interceptor'ов
У RequestMappingHandlerMapping есть поле списков обьектов типа Interceptor.
    
        <bean id="requestHandlerMapping" class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping"> 
     	<property name="interceptors"> 
     	<list> 
     	<ref bean="checkUserInterceptor" /> 
     	</list> 
     	</property> 
     	</bean> 

или так, если хотим отдельно

    <mvc:interceptors>
        <mvc:interceptor>
            <mvc:mapping path="/*"/>
            <bean class="com.springapp.mvc.interceptor.CheckUserInterceptor"/>
        </mvc:interceptor>
    </mvc:interceptors>

Валидация данных
===============
Проверка соответсвия определенным критериам.
**JSR 303: Bean Validation** -смысл такой что мы можем сразу в наши java обьекты (POJO) вставлять валидацию на уровне свойств наших обьектов. Она стандартная, это значит что она поддерживается везде где есть java код т.е. мы можем использовать Bean Validation во всех java фреймворках.

**Hibernae Validation** -  чаще называют reference implementation (RI) - это эталонная реализация которая берется по умолчанию.

Сохранение обьекта в сессии
===============

Чтобы обьект был доступен в нескольких запросах, его можно сохранить в сессии. В одной сессии может быть любое количество запросов клиента. Если между этими запросами не сохранять данные то эти данные будут теряться. Допустим если пользователь добавил несколько товаров в конзину и если эти данные не будут сохранены в сессии то в корзине ничего не окажется.

@ModelAttribute - если эта аннотиция указана в параметре метода. То с помощью этого атрибута мы можем получить значение модели (@ModelAttribute("user") User user) которое храниться в "user". Если в модель "user" мы записовали какие то данные, то они записоваются в переменную User user.

	@RequestMapping(value = "/check-user", method = RequestMethod.POST)
	public String checkUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "registration";
		}
		model.addAttribute("user", user);
	}

@ModelAttribute - сама инициализирует обьект User user, т.е. получается если нет сохраненного обьекта то @ModelAttribute создает этот обьект (но обьект не должен быть абстрактным классовм).
   
   	@RequestMapping(value="/", method = RequestMethod.GET)
	public ModelAndView registration(@ModelAttribute User user, HttpSession session) {
		user.setName("Userator");
		return new ModelAndView("registration", "user", user);
	}
			
**@ModelAttribute** может возвращять готовые значения модели которые уже сохранены и он может создавать сам если этого значения нет.

Если указан @SessionAttributes("user") и @ModelAttribute указан в качестве параметра (@ModelAttribu User user - в методе registration) и этого значения в качестве сохраненного параметра нет, то он это значение ищет в своей модели если в модели этого значения нет то это значение он ищет в сессии. Если в сессии этой ошибки то выводит ошибку

    HTTP Status 500 - Expected session attribute 'user'
    type Exception report
    message Expected session attribute 'user'
    description The server encountered an internal error that prevented it from fulfilling this request.
    exception
    
    org.springframework.web.HttpSessionRequiredException: Expected session attribute 'user'
        org.springframework.web.method.annotation.ModelFactory.initModel(ModelFactory.java:114)
        org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandleMethod(RequestMappingHandlerAdapter.java:758)
        org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:721)
        org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:83)
        org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:943)
        org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:877)
        org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:966)
        org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:857)
        javax.servlet.http.HttpServlet.service(HttpServlet.java:622)
        org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:842)
        javax.servlet.http.HttpServlet.service(HttpServlet.java:729)
        org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:52)
        org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:88)
        org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)
    
    note The full stack trace of the root cause is available in the Apache Tomcat/8.0.28 logs.
    
@SessionAttributes - нужен для того что бы сохранять значения которые вы создаете по ходу работы контроллера. Рекоменндуется сохранять в сессии т.к. по умолчанию то что вы создаете в модели, все это сохраняется между вашими запросами или в одном запросе. Что бы сохранить значения какой то модели и использовать его в разным запросов, мы должны прописовать его в SessionAttributes. 

Внутри модели обьект будет сохроняться только внутри запроса. 
Ecли нужно сохранять обьект между разными запросами то использовать сессии. 