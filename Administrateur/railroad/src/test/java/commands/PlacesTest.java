package commands;


import interpreter.Command;

import interpreter.CommandTest;

import java.util.List;


class PlacesTest extends CommandTest {



    @Override
    protected Command createCommand() {
        return new Places();
    }

    @Override
    protected List<String> invalid() {
        return List.of();
    }

    @Override
    protected List<String> valid() {
        return List.of("test1","test2","test3");
    }


}
