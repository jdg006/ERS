truncate "ERS".info, "ERS".company, "ERS".reimbursement, "ERS".user;

ALTER SEQUENCE "ERS".info_id_seq RESTART WITH 1;
ALTER SEQUENCE "ERS".company_id_seq RESTART WITH 1;
ALTER SEQUENCE "ERS".reimbursement_id_seq RESTART WITH 1;
ALTER SEQUENCE "ERS".user_id_seq RESTART WITH 1;

insert into "ERS".company (name, hq_address) values ('Revature', '2190 American plaza');
insert into "ERS"."user" (email, password, permission_level, superior_id, approved, company_id)
values ('j@j.com', 'wordofpass', 2, null, true, 1);
insert into "ERS".info (first_name, last_name, phone, address, position, img_src, user_id)
values ('Joe', 'Gallagher', '17137612637', '210 nonbiz ave', 'boss of all bosses', 'https://awebsite.com', 1);
insert into "ERS".reimbursement (emp_id, man_id, amt, reason, date, denied, approved) 
values (1, 1, 1000,'legitimate reason', '12/30/06', false, false);

insert into "ERS".company (name, hq_address) values ('Company 1', '2190 American plaza');
insert into "ERS"."user" (email, password, permission_level, superior_id, approved, company_id)
values ('a@a.com', 'wordofpass', 2, null, true, 1);
insert into "ERS".info (first_name, last_name, phone, address, position, img_src, user_id)
values ('Jon', 'Gallagher', '17137612637', '210 nonbiz ave', 'boss of all bosses', 'https://awebsite.com', 1);
insert into "ERS".reimbursement (emp_id, man_id, amt, reason, date, denied, approved) 
values (2, 1, 1000,'legitimate reason', '12/30/06', false, false);

insert into "ERS".company (name, hq_address) values ('Company 2', '2190 American plaza');
insert into "ERS"."user" (email, password, permission_level, superior_id, approved, company_id)
values ('b@b.com', 'wordofpass', 2, null, true, 1);
insert into "ERS".info (first_name, last_name, phone, address, position, img_src, user_id)
values ('Josh', 'Gallagher', '17137612637', '210 nonbiz ave', 'boss of all bosses', 'https://awebsite.com', 1);
insert into "ERS".reimbursement (emp_id, man_id, amt, reason, date, denied, approved) 
values (3, 1, 1000,'legitimate reason', '12/30/06', false, false);