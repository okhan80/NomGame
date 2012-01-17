package com.khangames.framework;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: omarkhan
 * Date: 1/9/12
 * Time: 2:35 PM
 * To change this template use File | Settings | File Templates.
 */
public interface FileIO {
    public InputStream readAsset(String fileName) throws IOException;
    
    public InputStream readFile(String fileName) throws IOException;
    
    public OutputStream writeFile(String fileName) throws IOException;
}
