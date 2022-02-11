package se.natusoft.tools.modelish;

public interface TestModel extends ModelishModel<TestModel> {

    String name();
    TestModel name(String name);

    int age();
    TestModel age(int age);

    String address();
    TestModel address(String address);

}
