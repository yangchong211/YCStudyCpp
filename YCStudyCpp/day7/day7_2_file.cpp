//
// Created by 杨充 on 2023/7/5.
//

#include <iostream>
#include "ostream"
#include "fstream"
using namespace std;

//7.2.1.1 打开方式
void test7_2_1_1();
//7.2.1.2 默认打开模式
void test7_2_1_2();
//7.2.1.3 打开文件同时创建文件
void test7_2_1_3();

//7.2.2.1 文件流说明
void test7_2_2_1();
//7.2.2.2 键盘输入写文本文件
void test7_2_2_2();
//7.2.2.3 写入文本内容到文件
void test7_2_2_3();
//7.2.2.4 读取文本文件
void test7_2_2_4();
//7.2.2.5 二进制文件的读/写
void test7_2_2_5();
//7.2.2.6 实现文件复制
void test7_2_2_6();
//7.2.2.7 关闭文件
void test7_2_2_7();


//7.2.3.1 文件错误与状态
void test7_2_3_1();
//7.2.3.2 文件的追加
void test7_2_3_2();
//7.2.3.3 文件结尾的判断
void test7_2_3_3();
//7.2.3.4 在指定位置读/写文件
void test7_2_3_4();


int main() {
//    test7_2_1_1();
//    test7_2_1_2();
    test7_2_1_3();

//    test7_2_2_1();
//    test7_2_2_2();
//    test7_2_2_3();
//    test7_2_2_4();
//    test7_2_2_5();
    test7_2_2_6();
//    test7_2_2_7();

    test7_2_3_1();
    test7_2_3_2();
    test7_2_3_3();
    test7_2_3_4();
    return 0;
}

//7.2.1.1 打开方式
void test7_2_1_1() {
    //在从文件读取信息或者向文件写入信息之前，必须先打开文件。
    //ostream 和 fstream 对象都可以用来打开文件进行写操作，如果只需要打开文件进行读操作，则使用 ifstream 对象。

    char data[100];
    ofstream outfile;
    //打开文件
    //在从文件读取信息或者向文件写入信息之前，必须先打开文件。
    //ofstream 和 fstream 对象都可以用来打开文件进行写操作，如果只需要打开文件进行读操作，则使用 ifstream 对象。
    outfile.open("yc.txt");
    cout << "Writing to the file" << endl;
    cout << "Enter your name: \n";
    cin.getline(data, 100);
}

//7.2.1.2 默认打开模式
void test7_2_1_2() {
    //在从文件读取信息或者向文件写入信息之前，必须先打开文件。
    //ofstream 和 fstream 对象都可以用来打开文件进行写操作，如果只需要打开文件进行读操作，则使用 ifstream 对象。
    //下面是 open() 函数的标准语法，open() 函数是 fstream、ifstream 和 ofstream 对象的一个成员。
    //void open(const char *filename, ios::openmode mode);
    //在这里，open() 成员函数的第一参数指定要打开的文件的名称和位置，第二个参数定义文件被打开的模式。
    //模式标志	描述
    //ios::app	追加模式。所有写入都追加到文件末尾。
    //ios::ate	文件打开后定位到文件末尾。
    //ios::in	打开文件用于读取。
    //ios::out	打开文件用于写入。
    //ios::trunc	如果该文件已经存在，其内容将在打开文件之前被截断，即把文件长度设为 0。

    //您可以把以上两种或两种以上的模式结合使用。例如，如果您想要以写入模式打开文件，并希望截断文件，以防文件已存在，那么您可以使用下面的语法：
    //ofstream outfile;
    //outfile.open("file.dat", ios::out | ios::trunc );
}

//7.2.1.3 打开文件同时创建文件
void test7_2_1_3() {
    cout << "7.2.1.3 打开文件同时创建文件" << endl;
    //在C++中，您可以使用std::ofstream类来打开文件并创建文件（如果文件不存在）。以下是一个示例代码：
    //std::ofstream file("ycdoubi.txt");
    //请注意，使用std::ofstream打开文件时，文件将以写入模式打开。如果文件已经存在，它将被截断为零长度。
    //如果您想以追加模式打开文件，可以使用std::ofstream::app标志，如下所示：这将在打开文件时将文件指针定位到文件末尾，以便在文件中追加内容。
    std::ofstream file("ycdoubi.txt", std::ios::app);
    //使用std::ofstream类创建了一个名为file的对象，并传递文件路径作为构造函数的参数。
    //如果文件不存在，它将被创建。如果无法打开文件，我们可以根据需要进行错误处理。在这个示例中，我们简单地打印了一个错误消息。
    if (!file) {
        std::cout << "无法打开文件" << std::endl;
        return;
    }
    //一旦文件打开成功，您可以使用file对象进行文件操作，如写入内容或读取文件。完成后，使用close函数关闭文件。
    file.close();
    std::cout << "打开文件同时创建文件" << std::endl;
}


//7.2.2.1 文件流说明
void test7_2_2_1() {
    //本教程介绍如何从文件读取流和向文件写入流。这就需要用到 C++ 中另一个标准库 fstream，它定义了三个新的数据类型：
    //数据类型	描述
    //ofstream	该数据类型表示输出文件流，用于创建文件并向文件写入信息。
    //ifstream	该数据类型表示输入文件流，用于从文件读取信息。
    //fstream	该数据类型通常表示文件流，且同时具有 ofstream 和 ifstream 两种功能，这意味着它可以创建文件，向文件写入信息，从文件读取信息。
}

//7.2.2.2 键盘输入写文本文件
void test7_2_2_2() {
    char data[100];
    ofstream outfile;
    //打开文件
    //在从文件读取信息或者向文件写入信息之前，必须先打开文件。
    //ofstream 和 fstream 对象都可以用来打开文件进行写操作，如果只需要打开文件进行读操作，则使用 ifstream 对象。
    outfile.open("yc.txt");
    cout << "Writing to the file" << endl;
    cout << "Enter your name: \n";
    //键盘输入数据
    cin.getline(data, 100);

    // 向文件写入用户输入的数据
    // 使用流插入运算符（ << ）向文件写入信息，就像使用该运算符输出信息到屏幕上一样。
    // 唯一不同的是，在这里您使用的是 ofstream 或 fstream 对象，而不是 cout 对象。
    outfile << data << endl;
    cout << "Enter your age: ";
    cin >> data;
    cin.ignore();

    // 再次向文件写入用户输入的数据
    outfile << data << endl;

    // 关闭打开的文件
    //关闭文件
    //当 C++ 程序终止时，它会自动关闭刷新所有流，释放所有分配的内存，并关闭所有打开的文件。但程序员应该养成一个好习惯，在程序终止前关闭所有打开的文件。
    //下面是 close() 函数的标准语法，close() 函数是 fstream、ifstream 和 ofstream 对象的一个成员。
    outfile.close();
}


//7.2.2.3 写入文本内容到文件
void test7_2_2_3() {
    ofstream file("yc1.txt"); // 创建一个输出文件流对象
    if (file.is_open()) {
        file << "Hello, World!" << std::endl; // 写入文本内容
        file << "This is a sample file." << std::endl;
        file.close(); // 关闭文件
        std::cout << "File written successfully." << std::endl;
    } else {
        std::cout << "Failed to open the file." << std::endl;
    }
}

//7.2.2.4 读取文本文件
void test7_2_2_4() {
    //在 C++ 编程中，我们使用流提取运算符（ >> ）从文件读取信息，就像使用该运算符从键盘输入信息一样。
    //唯一不同的是，在这里您使用的是 ifstream 或 fstream 对象，而不是 cin 对象。

    //首先包含了<iostream>、<fstream>和<string>头文件，分别用于输入输出、文件操作和字符串处理。
    //然后，我们创建了一个ifstream对象file，并指定要读取的文件名为"yc.txt"。
    //接下来，我们使用is_open()方法检查文件是否成功打开。如果文件成功打开，我们使用std::getline()函数逐行读取文件内容，并将每行内容存储在std::string对象line中。
    //然后，我们输出每行内容到标准输出，这里使用std::cout打印到控制台。
    //最后，我们使用close()方法关闭文件，并根据需要输出相应的消息。
    //请注意，在读取文件内容时，我们使用std::getline()函数逐行读取，直到文件结束。每次读取一行内容后，我们输出到标准输出。


    ifstream infile;
    infile.open("yc.txt");
    cout << "Reading from the file" << endl;
    // 使用流提取运算符（ >> ）从文件读取信息，就像使用该运算符从键盘输入信息一样。
    // 唯一不同的是，在这里您使用的是 ifstream 或 fstream 对象，而不是 cin 对象。
    if (infile.is_open()) {
        std::string line;
        char data[100];
        while (std::getline(infile, line)) { // 逐行读取文件内容
            std::cout << "输出每行内容 ：" << line << std::endl; // 输出每行内容
        }
        infile.close();
        std::cout << "File read successfully." << std::endl;
    } else {
        std::cout << "Failed to open the file." << std::endl;
    }
}


//7.2.2.5 二进制文件的读/写
void test7_2_2_5() {
    //首先包含了<iostream>和<fstream>头文件，分别用于输入输出和文件操作。
    //然后，我们创建了一个ofstream对象outFile，并指定要写入的二进制文件名为"yc.bin"，并使用std::ios::binary标志来指示以二进制模式打开文件。
    //接下来，我们使用is_open()方法检查文件是否成功打开。如果文件成功打开，我们可以使用write()方法将数据以二进制形式写入文件。在示例中，我们写入了一个整数数组。
    //最后，我们使用close()方法关闭文件，并根据需要输出相应的消息。
    //接下来，我们创建了一个ifstream对象inFile，并指定要读取的二进制文件名为"yc.bin"，同样使用std::ios::binary标志来指示以二进制模式打开文件。
    //然后，我们使用is_open()方法检查文件是否成功打开。如果文件成功打开，我们可以使用read()方法从文件中读取二进制数据。在示例中，我们读取了一个整数数组。
    //最后，我们输出读取的数据到标准输出。
    //请注意，在读取和写入二进制文件时，我们使用reinterpret_cast来进行类型转换，以便正确地读取和写入二进制数据。

    // 写入二进制文件
    ofstream outFile("yc.bin",ios::out | ios::binary);
    if (outFile.is_open()) {
        int data[5] = {1,2,3,4,5};
        //将数组转化为char
        //outFile.write(data,sizeof(data));
        outFile.write(reinterpret_cast<const char*>(data), sizeof(data));
        std::cout << "Binary file written successfully." << std::endl;
    } else {
        std::cout << "Failed to open the file for writing." << std::endl;
    }

    //读取二进制文件
    ifstream inFile("yc.bin",ios::binary | ios::in);
    if (inFile.is_open()) {
        int data[5];
        inFile.read(reinterpret_cast<char*>(data), sizeof(data));
        for (int i = 0; i < 5; i++) {
            std::cout << data[i] << " ";
        }
        inFile.close();
        std::cout << std::endl << "Binary file read successfully." << std::endl;
    } else {
        std::cout << "Failed to open the file for reading." << std::endl;
    }
}

//7.2.2.6 实现文件复制
void test7_2_2_6() {
    cout << "7.2.2.6 实现文件复制" << endl;
    //使用std::ifstream类打开源文件，并使用std::ofstream类创建目标文件。我们使用std::ios::binary标志来以二进制模式打开文件，以确保正确复制文件的内容。
    std::ifstream source_file("yc.txt",std::ios::binary);
    std::ofstream destination_file("ycdoubi.txt",std::ios::binary);
    if (!source_file) {
        std::cout << "无法打开源文件" << std::endl;
        return;
    }
    if (!destination_file) {
        std::cout << "无法创建目标文件" << std::endl;
        return;
    }
    char ch;
    //使用get函数从源文件逐个字节读取内容，并使用put函数将字节写入目标文件，从而实现文件的复制。这个过程会一直进行，直到源文件的末尾。
    while (source_file.get(ch)) {
        destination_file.put(ch);
    }
    //请注意，这个示例是逐个字节复制文件的简单实现。对于大型文件，逐个字节的复制可能效率较低。
    //在实际应用中，您可以使用更高效的方法，如缓冲区复制或使用std::copy函数。
    source_file.close();
    destination_file.close();
    std::cout << "文件复制完成" << std::endl;
}

//7.2.2.7 关闭文件
void test7_2_2_7() {
    //当 C++ 程序终止时，它会自动关闭刷新所有流，释放所有分配的内存，并关闭所有打开的文件。但程序员应该养成一个好习惯，在程序终止前关闭所有打开的文件。
    //下面是 close() 函数的标准语法，close() 函数是 fstream、ifstream 和 ofstream 对象的一个成员。
    //void close();
}



//7.2.3.1 文件错误与状态
void test7_2_3_1() {
    //std::fstream类中定义了一些成员函数和状态标志，用于检测和处理文件错误和状态。以下是一些常用的状态标志和相关的成员函数：
    //fail()：检测文件操作是否失败。返回true表示失败，返回false表示成功。
    //bad()：检测文件流是否处于错误状态。返回true表示错误，返回false表示正常。
    //eof()：检测文件流是否到达文件末尾。返回true表示到达末尾，返回false表示未到达末尾。
    //good()：检测文件流是否处于正常状态。返回true表示正常，返回false表示出现错误。
    //clear()：清除文件流的错误状态标志。
    //rdstate()：返回当前文件流的状态标志。
    //setstate()：设置文件流的状态标志。
    //通过使用这些成员函数和状态标志，可以检测和处理文件操作中的错误和状态。例如，可以使用fail()函数来检测文件读取操作是否失败，然后使用clear()函数来清除错误状态标志。
    cout << "7.2.3.1 文件错误与状态" << endl;
//    std::ifstream file("example.txt");
    std::ifstream file("yc.txt");
    if (!file) {
        //我们检测文件是否成功打开，如果打开失败，则输出错误信息并返回。
        std::cerr << "Failed to open the file." << std::endl;
        return;
    }
    int num;
    //然后，我们使用while循环从文件中读取整数，并检测读取操作是否失败。如果失败，则输出错误信息并清除错误状态标志。
    while (file >> num) {
        if (file.fail()) {
            std::cerr << "Error reading the file." << std::endl;
            file.clear();  // 清除错误状态标志
            break;
        }
        std::cout << num << " ";
    }
    if (file.eof()) {
        //最后，我们检测是否到达文件末尾，并关闭文件。
        std::cout << std::endl << "End of file reached." << std::endl;
    }
    file.close();
    std::cerr << "file to close" << std::endl;
    //在上述示例中，我们打开了一个名为example.txt的文件，并使用std::ifstream对象file进行读取操作。
}

//7.2.3.2 文件的追加
void test7_2_3_2() {
    //在C++中，可以使用文件流对象的std::ofstream类来实现文件的追加操作。std::ofstream类用于文件的输出操作，可以创建新文件或打开已存在的文件进行写入。
    //要实现文件的追加，需要在打开文件时指定追加模式。可以使用std::ios::app标志来指定追加模式，它会将数据追加到文件的末尾而不是覆盖原有内容。
    cout << "7.2.3.2 文件的追加" << endl;
    std::ofstream file("yc.txt" , std::ios::app);
    if (!file) {
        std::cerr << "Failed to open the file." << std::endl;
        return;
    }
    file << "This is a new line." << std::endl;
    file << "This is another line." << std::endl;
    file.close();

    //并使用std::ofstream对象file进行写入操作。通过在打开文件时使用std::ios::app标志，我们指定了追加模式。
    //然后，我们使用<<运算符将数据写入文件。这些数据将被追加到文件的末尾，而不会覆盖原有内容。
    //通过使用追加模式，我们可以将新的数据追加到文件的末尾，而不会影响原有内容。这对于需要在现有文件中添加新数据的情况非常有用，例如日志文件的记录。

    std::ifstream inFile("yc.txt" , std::ios::binary | std::ios::in);
    if (inFile.is_open()) {
        int data[5];
        inFile.read(reinterpret_cast<char*>(data), sizeof(data));
        for (int i = 0; i < 5; i++) {
            std::cout << data[i] << " " <<endl;
        }
        inFile.close();
        std::cout << std::endl << "Binary file read successfully." << std::endl;
    } else {
        std::cout << "Failed to open the file for reading." << std::endl;
    }
}

//7.2.3.3 文件结尾的判断
void test7_2_3_3() {
    //在C++中，可以使用文件流对象的eof()函数来判断文件是否已经到达结尾。eof()函数是std::istream和std::ostream类的成员函数，用于检测文件流是否已经到达文件末尾。
    //eof()函数返回一个bool值，如果文件流已经到达文件末尾，则返回true，否则返回false。
    //以下是一个示例，演示了如何使用eof()函数来判断文件是否已经到达结尾：
    cout << "7.2.3.3 文件结尾的判断" << endl;
    std::ifstream file("yc.txt");
    if (!file) {
        //首先，我们检测文件是否成功打开，如果打开失败，则输出错误信息并返回。
        std::cerr << "Failed to open the file." << std::endl;
        return;
    }
    std::string line;
    //使用std::getline()函数从文件中逐行读取数据，并将每行数据输出到控制台。
    while (std::getline(file,line)) {
        std::cout << line << std::endl;
    }
    if (file.eof()) {
        //使用eof()函数检测文件是否已经到达结尾。如果到达结尾，则输出相应的提示信息。
        std::cout << "End of file reached." << std::endl;
    }
    file.close();
}

//7.2.3.4 在指定位置读/写文件
void test7_2_3_4() {
    //在C++中，可以使用文件流对象的seekg()和seekp()函数来在指定位置进行文件的读取和写入操作。这两个函数用于设置文件流的读取和写入位置。
    //seekg()函数用于设置输入文件流的读取位置，而seekp()函数用于设置输出文件流的写入位置。这两个函数都接受一个参数，表示要设置的位置。
    //以下是一个示例，演示了如何在指定位置读取和写入文件：
    cout << "7.2.3.4 在指定位置读/写文件" << endl;
    std::fstream file("yc.txt",std::ios::in | std::ios::out);
    if (!file) {
        std::cerr << "Failed to open the file." << std::endl;
    }

    // 在指定位置读取文件
    file.seekg(5, std::ios::beg);  // 从文件开头偏移5个字节
    char ch;
    file >> ch;
    std::cout << "Character at position 5: " << ch << std::endl;

    // 在指定位置写入文件
    file.seekp(10, std::ios::beg);  // 从文件开头偏移10个字节
    file << "XYZ";
    file.close();
}

