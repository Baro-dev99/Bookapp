insert into publisher values('apress','Apress');
insert into publisher values('wesley','Addison-Wesley');
insert into publisher values('wileyj','John Wiley & Sons');
insert into publisher values('reilly','O''Reilly Media');
insert into publisher values('peaedu','Pearson Education');
insert into publisher values('wroxpr','Wrox Press');
insert into publisher values('mitpre','MIT Press');

insert into author values('erigam','Erich','Gamma');
insert into author values('raljoh','Ralph','Johnson');
insert into author values('johvli','John','Vlissides');
insert into author values('richel','Richard','Helm');
insert into author values('thocor','Thomas','Cormen');
insert into author values('chalei','Charles','Leiserson');
insert into author values('ronriv','Ronald','Rivest');
insert into author values('cliste','Clifford','Stein');

insert into book values('dperoo','Design Patterns: Elements of Reusable OO Software','wesley','2000-12-15');
insert into book values('intalg','Introduction to Algorithms','mitpre','2004-07-27');
insert into book values('javhow','Java, How to Program','wesley','1999-10-31');
insert into book values('theoco','Theory of Computation','wileyj','2018-01-20');


insert into book_has_authors values('dperoo','erigam');
insert into book_has_authors values('dperoo','raljoh');
insert into book_has_authors values('dperoo','johvli');
insert into book_has_authors values('dperoo','richel');
insert into book_has_authors values('intalg','thocor');
insert into book_has_authors values('intalg','chalei');
insert into book_has_authors values('intalg','ronriv');
insert into book_has_authors values('intalg','cliste');
insert into book_has_authors values('javhow','richel');
insert into book_has_authors values('javhow','cliste');
insert into book_has_authors values('theoco','chalei');
insert into book_has_authors values('theoco','ronriv');

insert into  copy values('dperoo01','dperoo',true,'New');
insert into  copy values('dperoo02','dperoo',true,'Used');
insert into  copy values('dperoo03','dperoo',true,'Used');
insert into  copy values('intalg01','intalg',true,'Old');
insert into  copy values('javhow01','javhow',true,'New');
insert into  copy values('javhow02','javhow',true,'New');
insert into  copy values('theoco01','theoco',true,'New');

insert into student values('20141001','fisrt1','last1');
insert into student values('20141002','fisrt2','last2');
insert into student values('20151001','fisrt3','last3');
insert into student values('20161001','fisrt4','last4');
insert into student values('20171001','fisrt5','last5');

insert into borrow (student_id,copy_code,loan_date) values('20141001','dperoo01','2016-01-01');
insert into borrow (student_id,copy_code,loan_date,return_date) values('20141001','theoco01','2016-01-01','2016-02-01');

insert into borrow (student_id,copy_code,loan_date,return_date) values('20141001','intalg01','2016-01-01','2016-02-01');
insert into borrow (student_id,copy_code,loan_date,return_date) values('20141002','theoco01','2020-01-01','2020-01-15');
insert into borrow (student_id,copy_code,loan_date) values('20161001','javhow02','2020-01-01');
insert into borrow (student_id,copy_code,loan_date,return_date) values('20151001','dperoo01','2016-01-01','2016-02-01');
insert into borrow (student_id,copy_code,loan_date,return_date) values('20151001','dperoo01','2016-01-01','2016-02-01');



