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
    
