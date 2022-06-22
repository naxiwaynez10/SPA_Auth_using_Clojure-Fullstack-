-- :name add-user! :! :n
-- :doc creates a new user record
INSERT INTO users
(first_name, last_name, email, pass, authkey)
VALUES (:first_name, :last_name, :email, :pass, :authkey)

-- :name update-user! :! :n
-- :doc updates an existing user record
UPDATE users
SET first_name = :first_name, last_name = :last_name, email = :email
WHERE id = :id

-- :name get-logged-user :? :1
-- :doc retrieves a user record given the id and authkey
SELECT * FROM users
WHERE id = :id AND authkey = :authkey

-- :name get-user :? :1
-- :doc retrieves a user record given the id
SELECT * FROM users
WHERE id = :id OR email = :id

-- :name users :? :*
-- :doc retrieves a user record 
SELECT * FROM users


-- :name delete-user! :! :n
-- :doc deletes a user record given the id
DELETE FROM users
WHERE id = :id OR email = :id


-- FOR PEOPLE

-- :name add-person! :!
-- :doc Adds people to the record
INSERT INTO people
(full_name, email, address, about, phone, role)
VALUES (:full_name, :email, :address, :about, :phone, :role)



-- :name get-person! :? :1
-- :doc retrieves a user record given the id
SELECT * FROM people
WHERE id = :id OR email = :id

-- :name update-person! :! :n
-- :doc updates an existing user record
UPDATE people
SET first_name = :full_name, email = :email, about = :about phone = :phone address = :address role = :role
WHERE id = :id OR email = :id

-- :name delete-person! :! :n
-- :doc deletes a user record given the id
DELETE FROM people
WHERE id = :id 

-- :name fetch-people! :? :*
-- :doc retrieves all contacts
SELECT * FROM people 
ORDER BY id DESC