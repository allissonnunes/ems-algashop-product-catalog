package br.dev.allissonnunes.algashop.product.catalog.infrastructure.security;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

public interface SecurityAnnotations {

    @Target({ ElementType.METHOD, ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    @Documented
    @PreAuthorize("hasAuthority('SCOPE_products:read')")
    @interface CanReadProducts {

    }

    @Target({ ElementType.METHOD, ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    @Documented
    @PreAuthorize("hasAuthority('SCOPE_products:write')")
    @interface CanWriteProducts {

    }

    @Target({ ElementType.METHOD, ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    @Documented
    @PreAuthorize("hasAuthority('SCOPE_products:stock:write')")
    @interface CanWriteProductsStock {

    }

    @Target({ ElementType.METHOD, ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    @Documented
    @PreAuthorize("hasAuthority('SCOPE_categories:read')")
    @interface CanReadCategories {

    }

    @Target({ ElementType.METHOD, ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    @Documented
    @PreAuthorize("hasAuthority('SCOPE_categories:write')")
    @interface CanWriteCategories {

    }

}
