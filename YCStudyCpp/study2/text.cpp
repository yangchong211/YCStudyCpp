//
// Created by 杨充 on 2023/6/8.
//

#include "text.h"
#include <iostream>
using namespace std;

void text::add() {
    a+10;
}

void text::sum(int i) {
    b+=i;
}

int text::count(int j) {
    return c+j;
}

text::text(int a1, int b1) {
    a = a1;
    b = b1;
}

text::~text() {

}


int main(){
    // 手动分配内存 （不推荐使用）
    text *a = new text(1,2);
    a->sum(3);
    delete a;

    // 自动分配内存 （推荐使用）
    text a2 = text(1,2);
    a2.count(4);
}


