package me.noeffort.nuggetmod.common.capability.world;

import java.util.ArrayList;
import java.util.List;

public class HasBlock implements IHasBlock {

    private List<String> blocks;

    public HasBlock() {
        this.blocks = new ArrayList<>();
    }

    @Override
    public boolean hasBlock(String block) {
        return this.blocks.contains(block);
    }

    @Override
    public List<String> getBlocks() {
        return this.blocks;
    }

    @Override
    public void setBlocks(List<String> blocks) {
        this.blocks = blocks;
    }

    @Override
    public void addBlock(String block) {
        this.blocks.add(block);
    }

    @Override
    public void removeBlock(String block) {
        this.blocks.remove(block);
    }

}
