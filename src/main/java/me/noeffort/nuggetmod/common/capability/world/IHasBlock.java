package me.noeffort.nuggetmod.common.capability.world;

import java.util.List;

public interface IHasBlock {

    boolean hasBlock(String block);

    List<String> getBlocks();

    void setBlocks(List<String> blocks);
    void addBlock(String block);
    void removeBlock(String block);

}
