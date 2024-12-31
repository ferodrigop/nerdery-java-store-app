CREATE TABLE app_order
(
    id         UUID                        NOT NULL,
    total      DECIMAL                     NOT NULL,
    user_id    UUID                        NOT NULL,
    status     VARCHAR(255)                NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_app_order PRIMARY KEY (id)
);

CREATE TABLE app_user
(
    id                UUID                        NOT NULL,
    first_name        VARCHAR(255)                NOT NULL,
    last_name         VARCHAR(255)                NOT NULL,
    email             VARCHAR(255)                NOT NULL,
    password          VARCHAR(255)                NOT NULL,
    role_id           UUID                        NOT NULL,
    is_email_verified BOOLEAN                     NOT NULL,
    is_deleted        BOOLEAN                     NOT NULL,
    created_at        TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at        TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_app_user PRIMARY KEY (id)
);

CREATE TABLE cart
(
    id         UUID                        NOT NULL,
    user_id    UUID                        NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_cart PRIMARY KEY (id)
);

CREATE TABLE cart_item
(
    id         UUID    NOT NULL,
    quantity   INTEGER NOT NULL,
    cart_id    UUID    NOT NULL,
    product_id UUID    NOT NULL,
    CONSTRAINT pk_cart_item PRIMARY KEY (id)
);

CREATE TABLE category
(
    id   UUID         NOT NULL,
    name VARCHAR(255) NOT NULL,
    CONSTRAINT pk_category PRIMARY KEY (id)
);

CREATE TABLE notification
(
    id           UUID                        NOT NULL,
    message      VARCHAR(255)                NOT NULL,
    recipient_id UUID                        NOT NULL,
    created_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_notification PRIMARY KEY (id)
);

CREATE TABLE order_item
(
    id         UUID    NOT NULL,
    quantity   INTEGER NOT NULL,
    unit_price DECIMAL NOT NULL,
    order_id   UUID    NOT NULL,
    product_id UUID    NOT NULL,
    CONSTRAINT pk_order_item PRIMARY KEY (id)
);

CREATE TABLE payment
(
    id                       UUID                        NOT NULL,
    stripe_payment_intent_id VARCHAR(255)                NOT NULL,
    amount                   DECIMAL                     NOT NULL,
    currency                 VARCHAR(255)                NOT NULL,
    status                   VARCHAR(255)                NOT NULL,
    order_id                 UUID                        NOT NULL,
    created_at               TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_payment PRIMARY KEY (id)
);

CREATE TABLE product
(
    id          UUID                        NOT NULL,
    name        VARCHAR(255)                NOT NULL,
    description TEXT                        NOT NULL,
    price       DECIMAL                     NOT NULL,
    stock       DECIMAL                     NOT NULL,
    is_enabled  BOOLEAN                     NOT NULL,
    category_id UUID                        NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_product PRIMARY KEY (id)
);

CREATE TABLE product_image
(
    id         UUID                        NOT NULL,
    image_url  VARCHAR(255)                NOT NULL,
    is_main    BOOLEAN                     NOT NULL,
    product_id UUID                        NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_product_image PRIMARY KEY (id)
);

CREATE TABLE product_like
(
    id         UUID                        NOT NULL,
    user_id    UUID                        NOT NULL,
    product_id UUID                        NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_product_like PRIMARY KEY (id)
);

CREATE TABLE refresh_token
(
    id         UUID                        NOT NULL,
    user_id    UUID                        NOT NULL,
    expires_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_refresh_token PRIMARY KEY (id)
);

CREATE TABLE role
(
    id        UUID         NOT NULL,
    role_name VARCHAR(255) NOT NULL,
    CONSTRAINT pk_role PRIMARY KEY (id)
);

CREATE TABLE role_permission
(
    id         UUID         NOT NULL,
    permission VARCHAR(255) NOT NULL,
    role_id    UUID         NOT NULL,
    CONSTRAINT pk_role_permission PRIMARY KEY (id)
);

CREATE TABLE stripe_webhook
(
    id          UUID                        NOT NULL,
    event_id    VARCHAR(255)                NOT NULL,
    event_type  VARCHAR(255)                NOT NULL,
    payload     VARCHAR(255)                NOT NULL,
    received_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_stripe_webhook PRIMARY KEY (id)
);

CREATE TABLE verification_code
(
    id          UUID                        NOT NULL,
    code        VARCHAR(255)                NOT NULL,
    expiry_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    user_id     UUID                        NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_verification_code PRIMARY KEY (id)
);

ALTER TABLE product_like
    ADD CONSTRAINT uc_a9e551098a17e099d3bb3cb55 UNIQUE (user_id, product_id);

ALTER TABLE app_user
    ADD CONSTRAINT uc_app_user_email UNIQUE (email);

ALTER TABLE role_permission
    ADD CONSTRAINT uc_role_permission_permission UNIQUE (permission);

ALTER TABLE role
    ADD CONSTRAINT uc_role_rolename UNIQUE (role_name);

ALTER TABLE verification_code
    ADD CONSTRAINT uc_verification_code_code UNIQUE (code);

ALTER TABLE verification_code
    ADD CONSTRAINT uc_verification_code_user UNIQUE (user_id);

ALTER TABLE app_order
    ADD CONSTRAINT FK_APP_ORDER_ON_USER FOREIGN KEY (user_id) REFERENCES app_user (id);

ALTER TABLE app_user
    ADD CONSTRAINT FK_APP_USER_ON_ROLE FOREIGN KEY (role_id) REFERENCES role (id);

ALTER TABLE cart_item
    ADD CONSTRAINT FK_CART_ITEM_ON_CART FOREIGN KEY (cart_id) REFERENCES cart (id);

ALTER TABLE cart_item
    ADD CONSTRAINT FK_CART_ITEM_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE cart
    ADD CONSTRAINT FK_CART_ON_USER FOREIGN KEY (user_id) REFERENCES app_user (id);

ALTER TABLE notification
    ADD CONSTRAINT FK_NOTIFICATION_ON_RECIPIENT FOREIGN KEY (recipient_id) REFERENCES app_user (id);

ALTER TABLE order_item
    ADD CONSTRAINT FK_ORDER_ITEM_ON_ORDER FOREIGN KEY (order_id) REFERENCES app_order (id);

ALTER TABLE order_item
    ADD CONSTRAINT FK_ORDER_ITEM_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE payment
    ADD CONSTRAINT FK_PAYMENT_ON_ORDER FOREIGN KEY (order_id) REFERENCES app_order (id);

ALTER TABLE product_image
    ADD CONSTRAINT FK_PRODUCT_IMAGE_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE product_like
    ADD CONSTRAINT FK_PRODUCT_LIKE_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE product_like
    ADD CONSTRAINT FK_PRODUCT_LIKE_ON_USER FOREIGN KEY (user_id) REFERENCES app_user (id);

ALTER TABLE product
    ADD CONSTRAINT FK_PRODUCT_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES category (id);

ALTER TABLE refresh_token
    ADD CONSTRAINT FK_REFRESH_TOKEN_ON_USER FOREIGN KEY (user_id) REFERENCES app_user (id);

ALTER TABLE role_permission
    ADD CONSTRAINT FK_ROLE_PERMISSION_ON_ROLE FOREIGN KEY (role_id) REFERENCES role (id);

ALTER TABLE verification_code
    ADD CONSTRAINT FK_VERIFICATION_CODE_ON_USER FOREIGN KEY (user_id) REFERENCES app_user (id);