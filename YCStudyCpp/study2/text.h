//
// Created by 杨充 on 2023/6/8.
//

#ifndef YCSTUDYCC_TEXT_H
#define YCSTUDYCC_TEXT_H

//1 . 属性与函数声明

class text {
private: //私有属性声明
    int a;
    void add();

protected: //子类可见声明
    int b;

public: //公开属性声明
    int c = 2;
    int count(int j);
    text(int a, int b); // 构造函数
    ~text(); //析构函数
    void sum(int i);
};


#endif //YCSTUDYCC_TEXT_H
