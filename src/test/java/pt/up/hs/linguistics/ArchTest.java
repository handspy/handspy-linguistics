package pt.up.hs.linguistics;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("pt.up.hs.linguistics");

        noClasses()
            .that()
                .resideInAnyPackage("pt.up.hs.linguistics.service..")
            .or()
                .resideInAnyPackage("pt.up.hs.linguistics.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..pt.up.hs.linguistics.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
