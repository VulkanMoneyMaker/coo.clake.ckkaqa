package com.dksazxv.dskf.com.Util;

import android.content.Context;
import android.opengl.GLES20;


public class ShaderUtils {
    public static int onCreateProgram(int vertexShaderId, int fragmentShaderId){
        final int programID = GLES20.glCreateProgram();
        if(programID == 0) return 0;

        GLES20.glAttachShader(programID, vertexShaderId);
        GLES20.glAttachShader(programID, fragmentShaderId);

        GLES20.glLinkProgram(programID);

        final int[] linkStatus = new int[1];
        GLES20.glGetProgramiv(programID, GLES20.GL_LINK_STATUS, linkStatus, 0);

        if(linkStatus[0] == 0){
            GLES20.glDeleteProgram(programID);
            return 0;
        }

        return programID;
    }

    public static int onCreateShader(Context context, int type, int shaderRawId){
        String shaderText = FileUtils.onReadTextFromRaw(context, shaderRawId);
        return ShaderUtils.onCreateShader(type, shaderText);
    }

    public static int onCreateShader(int type, String shaderText){
        final int shaderID = GLES20.glCreateShader(type);
        if(shaderID == 0) return 0;

        GLES20.glShaderSource(shaderID, shaderText);
        GLES20.glCompileShader(shaderID);

        final int[] compileStatus = new int[1];
        GLES20.glGetShaderiv(shaderID, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
        if(compileStatus[0] == 0){
            GLES20.glDeleteShader(shaderID);
            return 0;
        }
        return shaderID;
    }
}
