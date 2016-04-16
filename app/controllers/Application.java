package controllers;

import play.data.FormFactory;
import play.mvc.*;
import javax.inject.Inject;

public class Application extends Controller {

    @Inject
    FormFactory formFactory;

//    public Result index() {
//        return ok(index.render());
//    }
//
//    @Transactional
//    public Result addPerson() {
//        Person person = formFactory.form(Person.class).bindFromRequest().get();
//        JPA.em().persist(person);
//        return redirect(routes.Application.index());
//    }
//
//    @Transactional(readOnly = true)
//    public Result getPersons() {
//        List<Person> persons = (List<Person>) JPA.em().createQuery("select p from Person p").getResultList();
//        return ok(toJson(persons));
//    }
}
