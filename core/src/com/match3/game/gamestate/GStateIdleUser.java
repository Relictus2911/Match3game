package com.match3.game.gamestate;

import com.match3.game.registry.Registry;


public class GStateIdleUser implements GState{

    public Registry reg;

    public GStateIdleUser(Registry reg)
    {
        this.reg = reg;
    }

    @Override
    public void doLogic() {

    }
}
