-- Insert roles
INSERT INTO role (id, role_name)
VALUES (gen_random_uuid(), 'ROLE_USER'),
       (gen_random_uuid(), 'ROLE_MANAGER');

DO
$$
DECLARE
        user_role_id    UUID;
        manager_role_id UUID;
BEGIN
        -- Fetch role IDs into variables
SELECT id INTO user_role_id FROM role WHERE role_name = 'ROLE_USER';
SELECT id INTO manager_role_id FROM role WHERE role_name = 'ROLE_MANAGER';

-- Insert permissions for USER
INSERT INTO role_permission (id, permission, role_id)
VALUES (gen_random_uuid(), 'products:view', user_role_id),
       (gen_random_uuid(), 'product_details:view', user_role_id),
       (gen_random_uuid(), 'product:buy', user_role_id),
       (gen_random_uuid(), 'product:add_to_cart', user_role_id),
       (gen_random_uuid(), 'product:like', user_role_id),
       (gen_random_uuid(), 'my_orders:view', user_role_id);

-- Insert permissions for MANAGER
INSERT INTO role_permission (id, permission, role_id)
VALUES (gen_random_uuid(), 'product:create', manager_role_id),
       (gen_random_uuid(), 'product:update', manager_role_id),
       (gen_random_uuid(), 'product:delete', manager_role_id),
       (gen_random_uuid(), 'product:disable', manager_role_id),
       (gen_random_uuid(), 'clients_orders:view', manager_role_id),
       (gen_random_uuid(), 'product_image:upload', manager_role_id);

END;
$$