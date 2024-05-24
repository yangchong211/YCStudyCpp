#pragma once

#include"graduate.h"
#include"teacher.h"

class ta : virtual public Graduate, virtual public Teacher {
public:
    virtual void displayDetails();

    virtual void inputData();
};

void ta::inputData() {
    Teacher::inputData();
    Graduate::inputData();
}

void ta::displayDetails() {
    Teacher::displayDetails();
    Graduate::displayDetails();
}