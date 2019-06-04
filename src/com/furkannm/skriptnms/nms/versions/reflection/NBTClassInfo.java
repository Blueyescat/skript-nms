package com.furkannm.skriptnms.nms.versions.reflection;

import javax.annotation.Nullable;

import com.furkannm.skriptnms.SkriptNMS;
import com.furkannm.skriptnms.nms.NMSClasses;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;

public class NBTClassInfo<N extends NMSClasses> {

	private Class<N> c;
	private String codeName;
	private String[] users;
	
	public NBTClassInfo(Class<N> c, String codeName, String... users) {
		this.c = c;
		this.codeName = codeName;
		this.users = users;
	}
	
	public ClassInfo<N> getClassInfo() {
        return new ClassInfo<>(c, codeName)
        		.user(users)
        		.name("NBT Compound")
        		.since("0.1.3")        
                .parser(getParser());
    }
	
	public Parser<N> getParser() {
		return new Parser<N>() {
			
			@Override
			public String getVariableNamePattern() {
				return ".+";
			}

			@SuppressWarnings("unchecked")
			@Override
			@Nullable
			public N parse(String s, ParseContext context) {
				if (s.startsWith("nbt:{") && s.endsWith("}")) {
					N nbt = (N) SkriptNMS.getNMS().parseRawNBT(s.substring(4));
					return nbt;
				}
				return null;
			}
			
			@Override
			public String toString(N n, int arg1) {
				return n.toString();
			}

			@Override
			public String toVariableNameString(N n) {
				return n.toString();
			}
			
		};
	}
	

}
