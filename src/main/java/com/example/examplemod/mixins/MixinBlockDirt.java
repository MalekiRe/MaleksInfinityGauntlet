package com.example.examplemod.mixins;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistryInternal;
import net.minecraftforge.registries.IForgeRegistryModifiable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
@Mixin(value = ForgeRegistry.class, remap = false)
public abstract class MixinBlockDirt<V extends IForgeRegistryEntry<V>> implements IForgeRegistryInternal<V>, IForgeRegistryModifiable<V>{
	ResourceLocation firstResourceValue = new ResourceLocation("thermalfoundation:ore");
	Set<ResourceLocation> resourceLocations = new HashSet<ResourceLocation>();
	@Shadow 
	abstract int add(int id, V value);
	@Overwrite
	@Override
    public void registerAll(@SuppressWarnings("unchecked") V... values) {
		if(resourceLocations.isEmpty())
			fillResourceLocations();
        for (V value : values)
        	if(!resourceLocations.contains(value.getRegistryName()))
        		register(value);
    }
	@Overwrite
	@Override
    public void register(V value) {
		if(resourceLocations.isEmpty())
				fillResourceLocations();
		if(!resourceLocations.contains(value.getRegistryName()))
			add(-1, value);
    }
	
	public void fillResourceLocations() {
		String filePath = Loader.instance().getConfigDir().toString(), file = "/malekremoveregistries.txt";
		try {File myObj = new File(Loader.instance().getConfigDir() + file);
            if (myObj.createNewFile()) {}} catch (IOException e) {e.printStackTrace();}
        File text = new File(Loader.instance().getConfigDir() + file);
        Scanner scnr = null;
		try { scnr = new Scanner(text); } catch (FileNotFoundException e) { e.printStackTrace();}
		while(scnr.hasNext())
        	resourceLocations.add(new ResourceLocation(scnr.next()));
        
	}
}
