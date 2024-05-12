package examplemod.proxy;

import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;

public class ServerProxy {
    public Side getSide() {
        return this.isClient() ? Side.CLIENT : Side.SERVER;
    }

    @SuppressWarnings("all")
    public <T extends ServerProxy> T getAs(Class<T> clazz) {
        return (T) this;
    }
    public boolean isClient() {return false;}
    public World getClientWorld() {return null;}
}
