create table publisher(
    id char(6) not null primary key,
    pname varchar(45) not null
);

create table book(
    code char(6) not null primary key,
    title varchar(90) not null,
	date bdate,
    publisher_id char(6) references publisher(id) not null
);

create table author(
    id char(6) not null primary key,
    first_name varchar(45) not null,
    last_name varchar(45) not null
);

create table book_has_authors(
    book_code char(6) not null references book(code),
    author_id char(6) not null references author(id),
    primary key(book_code,author_id)
);

create table copy (
    code char(8) not null primary key,
    book_code char(6) references book(code) not null,
    available boolean default true,
    status varchar(90)
);

create table student (
    id char(8) not null primary key,
    first_name varchar(45) not null,
    last_name varchar(45) not null
);

create table borrow(
    id integer primary key generated always as identity (START WITH 1, INCREMENT BY 1),
    student_id char(8) not null references student(id),
    copy_code char(8) not null references copy(code),
    loan_date date not null,
    return_date date
)