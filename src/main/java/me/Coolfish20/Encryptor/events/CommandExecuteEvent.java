package me.Coolfish20.Encryptor.events;

import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import me.Coolfish20.Encryptor.init.Register;
import net.minecraft.command.CommandSource;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

import static me.Coolfish20.Encryptor.tileentities.RecorderTileEntity.recordertile;

public class CommandExecuteEvent {


    public static String data;
    @SubscribeEvent
    public void record(CommandEvent event){
       CommandSource source = event.getParseResults().getContext().getSource();
       ParseResults<CommandSource> parseResults = event.getParseResults();//parsing results
       CommandNode<CommandSource> node = parseResults.getContext().getNodes().get(0).getNode();//node == element
       LiteralCommandNode literalCommandNode = (LiteralCommandNode) node;//LiteralCommandNode = command element as string
        if(literalCommandNode.getLiteral().equals("msg")) {
            double x = source.getEntity().getX();
            double y = source.getEntity().getY();
            double z = source.getEntity().getZ();
            AxisAlignedBB recordrange = new AxisAlignedBB(x + 7, y + 7, z + 7,
                    x - 7, y - 7, z - 7);
            if (recordrange.intersects(recordertile)) {
                List<TileEntity> tiles = source.getLevel().blockEntityList;
                List<BlockPos> pos = new ArrayList<>();
                List<BlockPos> sort = new ArrayList<>();
                //Putting all coordinates into hashmap
                tiles.removeIf(tile -> !recordrange.intersects(tile.getRenderBoundingBox()));//Removing all tiles that do not intersect with recordrange
                tiles.removeIf(tile -> !(tile.getType() == Register.RECORDER_TILE_ENTITY.get()));//Removing all non- RECORDER_TILE_ENTITIES

                for (TileEntity tile : tiles) {
                    pos.add(tile.getBlockPos());
                }
                //Comparing the block positions(getting closest tile)
                //Closest tile "catches" the message and doesn't let other tiles "catch" it(save it)
                if (pos.size() != 0) {
                    for (BlockPos position : pos) {
                        //if the current value is bigger, than the index one value
                        if (sort.size() != 0) {
                            //If the current value is closer to the center than sort's(list) first index
                            if (position.distSqr(new Vector3i(recordrange.getCenter().x, recordrange.getCenter().y, recordrange.getCenter().z))
                                    < sort.get(0).distSqr(new Vector3i(recordrange.getCenter().x, recordrange.getCenter().y, recordrange.getCenter().z))) {
                                sort.set(0, position);//First index
                            } else {
                                // If sort's(list) first index is smaller(closer to the center) than current value
                                if (position.distSqr(new Vector3i(recordrange.getCenter().x, recordrange.getCenter().y, recordrange.getCenter().z))
                                        > sort.get(0).distSqr(new Vector3i(recordrange.getCenter().x, recordrange.getCenter().y, recordrange.getCenter().z))) {
                                    sort.add(position);//Second index
                                }
                            }
                        } else {
                            sort.add(position);
                        }
                    }

                    TileEntity tile = source.getLevel().getBlockEntity(sort.get(0));
                    if (tile != null) {
                        int number = tile.getTileData().getInt("message_number") + 1;//Not sure if this will work
                        tile.getTileData().putInt("message_number", number);
                        data = source.getTextName()+ " to " + event.getParseResults().getReader().getString().substring(5);//fix this<<--
                        tile.getTileData().putString("message_" + number, data);
                        tile.setChanged();


                    }
                }
            }
        }




        // "r" command
        if(literalCommandNode.getLiteral().equals("r")) {
            double x = source.getEntity().getX();
            double y = source.getEntity().getY();
            double z = source.getEntity().getZ();
            AxisAlignedBB recordrange = new AxisAlignedBB(x + 7, y + 7, z + 7,
                    x - 7, y - 7, z - 7);
            if (recordrange.intersects(recordertile)) {
                List<TileEntity> tiles = source.getLevel().blockEntityList;
                List<BlockPos> pos = new ArrayList<>();
                List<BlockPos> sort = new ArrayList<>();
                //Putting all coordinates into hashmap
                tiles.removeIf(tile -> !recordrange.intersects(tile.getRenderBoundingBox()));//Removing all tiles that do not intersect with recordrange
                tiles.removeIf(tile -> !(tile.getType() == Register.RECORDER_TILE_ENTITY.get()));//Removing all non- RECORDER_TILE_ENTITIES

                for (TileEntity tile : tiles) {
                    pos.add(tile.getBlockPos());
                }
                //Comparing the block positions(getting closest tile)
                if (pos.size() != 0) {
                    for (BlockPos position : pos) {
                        //if the current value is bigger, than the index one value
                        if (sort.size() != 0) {
                            //If the current value is closer to the center than sort's(list) first index
                            if (position.distSqr(new Vector3i(recordrange.getCenter().x, recordrange.getCenter().y, recordrange.getCenter().z))
                                    < sort.get(0).distSqr(new Vector3i(recordrange.getCenter().x, recordrange.getCenter().y, recordrange.getCenter().z))) {
                                sort.set(0, position);//First index
                            } else {
                                // If sort's(list) first index is smaller(closer to the center) than current value
                                if (position.distSqr(new Vector3i(recordrange.getCenter().x, recordrange.getCenter().y, recordrange.getCenter().z))
                                        > sort.get(0).distSqr(new Vector3i(recordrange.getCenter().x, recordrange.getCenter().y, recordrange.getCenter().z))) {
                                    sort.set(1, position);//Second index
                                }
                            }
                        } else {
                            sort.add(position);
                        }
                    }

                    TileEntity tile = source.getLevel().getBlockEntity(sort.get(0));
                    if (tile != null) {
                        int number = tile.getTileData().getInt("message_number") + 1;//Not sure if this will work
                        tile.getTileData().putInt("message_number", number);
                        data = source.getTextName()+ " to " + event.getParseResults().getReader().getString().substring(3);//fix this<<--
                        tile.getTileData().putString("message_" + number, data);
                        tile.setChanged();


                    }
                }
            }
        }







        //"w" command
        if(literalCommandNode.getLiteral().equals("w")) {
            double x = source.getEntity().getX();
            double y = source.getEntity().getY();
            double z = source.getEntity().getZ();
            AxisAlignedBB recordrange = new AxisAlignedBB(x + 7, y + 7, z + 7,
                    x - 7, y - 7, z - 7);
            if (recordrange.intersects(recordertile)) {
                List<TileEntity> tiles = source.getLevel().blockEntityList;
                List<BlockPos> pos = new ArrayList<>();
                List<BlockPos> sort = new ArrayList<>();
                //Putting all coordinates into hashmap
                tiles.removeIf(tile -> !recordrange.intersects(tile.getRenderBoundingBox()));//Removing all tiles that do not intersect with recordrange
                tiles.removeIf(tile -> !(tile.getType() == Register.RECORDER_TILE_ENTITY.get()));//Removing all non- RECORDER_TILE_ENTITIES

                for (TileEntity tile : tiles) {
                    pos.add(tile.getBlockPos());
                }
                //Comparing the block positions(getting closest tile)
                if (pos.size() != 0) {
                    for (BlockPos position : pos) {
                        //if the current value is bigger, than the index one value
                        //If the next value is closer to the center
                        if (sort.size() != 0) {
                            //If the current value is closer to the center than sort's(list) first index
                            if (position.distSqr(new Vector3i(recordrange.getCenter().x, recordrange.getCenter().y, recordrange.getCenter().z))
                                    < sort.get(0).distSqr(new Vector3i(recordrange.getCenter().x, recordrange.getCenter().y, recordrange.getCenter().z))) {
                                sort.set(0, position);//First index
                            } else {
                                // If sort's(list) first index is smaller(closer to the center) than current value
                                if (position.distSqr(new Vector3i(recordrange.getCenter().x, recordrange.getCenter().y, recordrange.getCenter().z))
                                        > sort.get(0).distSqr(new Vector3i(recordrange.getCenter().x, recordrange.getCenter().y, recordrange.getCenter().z))) {
                                    sort.set(1, position);//Second index
                                }
                            }
                        } else {
                            sort.add(position);

                        }
                    }
                    TileEntity tile = source.getLevel().getBlockEntity(sort.get(0));
                    if (tile != null) {
                        int number = tile.getTileData().getInt("message_number") + 1;//Not sure if this will work
                        tile.getTileData().putInt("message_number", number);
                        data = source.getTextName()+ " to " + event.getParseResults().getReader().getString().substring(3);//fix this<<--
                        tile.getTileData().putString("message_" + number, data);
                        tile.setChanged();


                    }
                }
            }
        }


        //"tell" command
        if(literalCommandNode.getLiteral().equals("tell")) {
            double x = source.getEntity().getX();
            double y = source.getEntity().getY();
            double z = source.getEntity().getZ();
            AxisAlignedBB recordrange = new AxisAlignedBB(x + 7, y + 7, z + 7,
                    x - 7, y - 7, z - 7);
            if (recordrange.intersects(recordertile)) {
                List<TileEntity> tiles = source.getLevel().blockEntityList;
                List<BlockPos> pos = new ArrayList<>();
                List<BlockPos> sort = new ArrayList<>();
                //Putting all coordinates into hashmap
                tiles.removeIf(tile -> !recordrange.intersects(tile.getRenderBoundingBox()));//Removing all tiles that do not intersect with recordrange
                tiles.removeIf(tile -> !(tile.getType() == Register.RECORDER_TILE_ENTITY.get()));//Removing all non- RECORDER_TILE_ENTITIES
                //Adding tile positions to the list "pos"
                for (TileEntity tile : tiles) {
                    pos.add(tile.getBlockPos());
                }
                //Comparing the block positions(getting closest tile)
                if (pos.size() != 0) {
                    for (BlockPos position : pos) {
                        //if the current value is bigger, than the index one value
                        //If the next value is closer to the center
                        if (sort.size() != 0) {
                            //If the current value is closer to the center than sort's(list) first index
                            if (position.distSqr(new Vector3i(recordrange.getCenter().x, recordrange.getCenter().y, recordrange.getCenter().z))
                                    < sort.get(0).distSqr(new Vector3i(recordrange.getCenter().x, recordrange.getCenter().y, recordrange.getCenter().z))) {
                                sort.set(0, position);//First index
                            } else {
                                // If sort's(list) first index is smaller(closer to the center) than current value
                                if (position.distSqr(new Vector3i(recordrange.getCenter().x, recordrange.getCenter().y, recordrange.getCenter().z))
                                        > sort.get(0).distSqr(new Vector3i(recordrange.getCenter().x, recordrange.getCenter().y, recordrange.getCenter().z))) {
                                    sort.set(1, position);//Second index
                                }
                            }
                        } else {
                            sort.add(position);
                        }
                    }
                    TileEntity tile = source.getLevel().getBlockEntity(sort.get(0));
                    if (tile != null) {
                        int number = tile.getTileData().getInt("message_number") + 1;//Not sure if this will work
                        tile.getTileData().putInt("message_number", number);
                        data = source.getTextName()+ " to " +event.getParseResults().getReader().getString().substring(6);//fix this<<--
                        tile.getTileData().putString("message_" + number, data);
                        tile.setChanged();


                    }
                }

                }
            }








    }
}
