package org.lainsoft.commons.helpers;

import java.io.File;

public class PathHelper{
    
    private static String basepath;
    
    private static PathHelper internal;
    
    private PathHelper(String basepath){        
        this.basepath = this.basepath == null 
            ? (basepath == null || basepath.trim().equals("")) 
              ? new File("./").getAbsolutePath() 
              : basepath  
            : this.basepath;
    }

    public static PathHelper
    getInstance(){
        return internal == null ? internal = new PathHelper(null) : internal;
    }

    public static PathHelper
    getInstance(String basepath){
        return internal == null 
            ? internal = new PathHelper(basepath) 
            : basepath.equals(PathHelper.basepath) 
              ? internal 
              : new PathHelper(basepath);
    }
    
    
    public String
    getRealPath(String path){
        return new File (basepath+"/"+path).getAbsolutePath();
    }
}
