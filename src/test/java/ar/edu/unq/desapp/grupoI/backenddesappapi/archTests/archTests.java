package ar.edu.unq.desapp.grupoI.backenddesappapi.archTests;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public class archTests {

    private JavaClasses jc;

    @BeforeEach
    public void setup() {
        jc = new ClassFileImporter()
                .importPackages("ar.edu.unq.desapp.grupoI.backenddesappapi");
    }

    @Test
    public void Webservices_should_be_finish_with_Controller_word() {

        ArchRule myRule = classes()
                .that().resideInAPackage("..webservices..")
                .should().haveSimpleNameEndingWith("Controller");

        myRule.check(jc);
    }

    @Test
    void Services_should_be_finish_with_Service_word() {

        classes()
                .that().resideInAPackage("main.java.ar.edu.unq.desapp.grupoI.backenddesappapi") //Esto es porque si no toma los de la carpeta Test
                .should().haveSimpleNameEndingWith("Service")
                .check(jc);
    }

    @Test
    void Repositories_should_be_finish_with_Repository_word() {

        classes()
                .that().resideInAPackage("main.java.ar.edu.unq.desapp.grupoI.backenddesappapi")
                .should().haveSimpleNameEndingWith("Repository")
                .check(jc);
    }

    @Test
    void Jwt_Classes_should_be_begin_with_Jwt_word() {

        classes()
                .that().resideInAPackage("..auth..")
                .should().haveSimpleNameStartingWith("Jwt")
                .check(jc);
    }

    @Test
    void Config_Classes_should_have_Spring_Config_Anottation() {
        classes()
                .that().resideInAPackage("..config..")
                .should().beAnnotatedWith(Configuration.class)
                .check(jc);
    }

    @Test
    void Repositories_Classes_should_have_Spring_Repository_Anottation() {
        classes()
                .that().resideInAPackage("..repositories..")
                .should().beAnnotatedWith(Repository.class)
                .check(jc);
    }

    @Test
    public void no_Classes_with_Service_Annotation_should_reside_outside_Service_layer() {
        ArchRule rule = ArchRuleDefinition.noClasses()
                .that().areAnnotatedWith(Service.class)
                .should().resideOutsideOfPackage("..services..");
        rule.check(jc);
    }

    @Test
    public void no_Classes_with_Aspects_Annotation_should_reside_outside_Aspects_layer() {
        ArchRule rule = ArchRuleDefinition.noClasses()
                .that().areAnnotatedWith(Aspect.class)
                .should().resideOutsideOfPackage("..aspects..");
        rule.check(jc);
    }
}
