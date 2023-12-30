-- Insert data into the 'users' table with manually specified UUIDs as IDs
INSERT INTO users (id, email, image_url, password, provider, username)
VALUES ('a550559d-3c3c-4dbd-9a80-2c6b045b3e91', 'user1@example.com', 'url1', 'password1', 'LOCAL', 'user1'),
       ('b550559d-3c3c-4dbd-9a80-2c6b045b3e91', 'user2@example.com', 'url2', 'password2', 'LOCAL', 'user2'),
       ('c550559d-3c3c-4dbd-9a80-2c6b045b3e91', 'user3@example.com', 'url3', 'password3', 'LOCAL', 'user3');

-- Insert data into the 'groups' table with manually specified UUIDs as IDs
INSERT INTO groups (id, owner_id, currency, name)
VALUES ('d550559d-3c3c-4dbd-9a80-2c6b045b3e91', 'a550559d-3c3c-4dbd-9a80-2c6b045b3e91', 'USD', 'Group 1'),
       ('e550559d-3c3c-4dbd-9a80-2c6b045b3e91', 'b550559d-3c3c-4dbd-9a80-2c6b045b3e91', 'EUR', 'Group 2');

-- Insert data into the 'group_memberships' table with manually specified UUIDs as IDs
INSERT INTO group_memberships (group_id, user_id)
VALUES ('d550559d-3c3c-4dbd-9a80-2c6b045b3e91', 'a550559d-3c3c-4dbd-9a80-2c6b045b3e91'),
       ('d550559d-3c3c-4dbd-9a80-2c6b045b3e91', 'b550559d-3c3c-4dbd-9a80-2c6b045b3e91'),
       ('d550559d-3c3c-4dbd-9a80-2c6b045b3e91', 'c550559d-3c3c-4dbd-9a80-2c6b045b3e91'),
       ('e550559d-3c3c-4dbd-9a80-2c6b045b3e91', 'b550559d-3c3c-4dbd-9a80-2c6b045b3e91'),
       ('e550559d-3c3c-4dbd-9a80-2c6b045b3e91', 'c550559d-3c3c-4dbd-9a80-2c6b045b3e91');

-- Insert data into the 'expenses' table with manually specified UUIDs as IDs
INSERT INTO expenses (id, creditor_id, group_id, expense_type, name)
VALUES ('550559d3-c3c4-4dbd-9a80-2c6b045b3e91', 'a550559d-3c3c-4dbd-9a80-2c6b045b3e91',
        'd550559d-3c3c-4dbd-9a80-2c6b045b3e91', 'GROUP_EXPENSE', 'Expense 1'),
       ('650559d3-c3c4-4dbd-9a80-2c6b045b3e91', 'b550559d-3c3c-4dbd-9a80-2c6b045b3e91',
        'd550559d-3c3c-4dbd-9a80-2c6b045b3e91', 'GROUP_EXPENSE', 'Expense 2'),
       ('750559d3-c3c4-4dbd-9a80-2c6b045b3e91', 'c550559d-3c3c-4dbd-9a80-2c6b045b3e91',
        'd550559d-3c3c-4dbd-9a80-2c6b045b3e91', 'GROUP_EXPENSE', 'Expense 3');


-- Insert data into the 'expense_debts' table with manually specified UUIDs as IDs
-- Insert data into the 'expense_debts' table with manually specified UUIDs as IDs
INSERT INTO expense_debts (amount, debtor_id, expense_id, currency)
VALUES (10.0, 'c550559d-3c3c-4dbd-9a80-2c6b045b3e91', '550559d3-c3c4-4dbd-9a80-2c6b045b3e91', 'EUR'),
       (15.0, 'b550559d-3c3c-4dbd-9a80-2c6b045b3e91', '650559d3-c3c4-4dbd-9a80-2c6b045b3e91', 'USD'),
       (8.5, 'c550559d-3c3c-4dbd-9a80-2c6b045b3e91', '750559d3-c3c4-4dbd-9a80-2c6b045b3e91', 'GBP'),
       (12.0, 'a550559d-3c3c-4dbd-9a80-2c6b045b3e91', '550559d3-c3c4-4dbd-9a80-2c6b045b3e91', 'USD'),
       (18.0, 'b550559d-3c3c-4dbd-9a80-2c6b045b3e91', '750559d3-c3c4-4dbd-9a80-2c6b045b3e91', 'EUR'),
       (9.5, 'c550559d-3c3c-4dbd-9a80-2c6b045b3e91', '550559d3-c3c4-4dbd-9a80-2c6b045b3e91', 'USD');

