package com.example.classproject;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.animation.RectEvaluator;
import android.opengl.GLES20;
import android.opengl.Matrix;

public class Rectangle {
	

	private FloatBuffer mColumnPositions;
	private FloatBuffer mColumnColors;
	private FloatBuffer mColumnNormals;
	private final int mBytesPerFloat = 4; // how many bytes per float
	private final int mPositionDataSize = 3; //Size of the position data in elements 
	private int mPositionHandle; // passes in the position information
	private int mColorHandle; // passes in the color information
	private int mNormalHandle; // passes in the model normal information
	private final int mColorDataSize = 4; 
	private final int mNormalDataSize = 3;
	private float[] mModelMatrix = new float[16]; // Moves models from object space 
	private float[] mViewMatrix = new float[16]; // Camera
	private float[] mProjectionMatrix = new float[16]; // projects the scene to a 2D viewport
	private float[] mMVPMatrix = new float[16]; // final matrix, passed into the shader program
	private int mMVMatrixHandle; // passes in the model view matrix 
	private int mMVPMatrixHandle; // passes in the transformation matrix

	float Xo; // inital X
	float dX; // change x
	float sWidth; // width off the rectangle
	float Y1;// initial Y
	float tPlate; //height of rectangle
	float Zo=5; //initial Z
	float sDepth=-5; //change depth of cube
	
	public Rectangle(float Xo, float sWidth, float Y1, float tPlate) {

		final float[] floorPositionData = 	{
				
				// Front face
				Xo + dX, 		Y1, 			Zo,		//1		
				Xo + dX , 		Y1 - tPlate, 	Zo,		//2
				sWidth + dX, 	Y1, 			Zo, 	//3
				Xo + dX, 		Y1 - tPlate, 	Zo, 	//2
				sWidth + dX, 	Y1 - tPlate, 	Zo,		//4
				sWidth + dX, 	Y1, 			Zo,		//3

				// Right face
				sWidth + dX,	Y1, 			Zo,				
				sWidth + dX, 	Y1 - tPlate, 	Zo,
				sWidth + dX, 	Y1, 			-sDepth,
				sWidth + dX, 	Y1 - tPlate, 	Zo,				
				sWidth + dX, 	Y1 - tPlate, 	-sDepth,
				sWidth + dX, 	Y1, 			-sDepth,

				// Back face
				sWidth + dX, 	Y1, 			-sDepth,				
				sWidth + dX, 	Y1 - tPlate, 	-sDepth,
				Xo + dX, 		Y1, 			-sDepth,
				sWidth + dX,		Y1 - tPlate, 	-sDepth,				
				Xo + dX, 		Y1 - tPlate, 	-sDepth,
				Xo + dX, 		Y1, 			-sDepth,

				// Left face
				Xo + dX, 		Y1, 			-sDepth,				
				Xo + dX, 		Y1 - tPlate, 	-sDepth,
				Xo + dX, 		Y1, 			Zo, 
				Xo + dX, 		Y1 - tPlate, 	-sDepth,				
				Xo + dX, 		Y1 - tPlate, 	Zo, 
				Xo + dX, 		Y1, 			Zo, 

				// Top face
				Xo + dX, 		Y1, 			-sDepth,				
				Xo + dX, 		Y1, 			Zo, 
				sWidth + dX, 	Y1, 			-sDepth, 
				Xo + dX, 		Y1, 			Zo, 				
				sWidth + dX, 	Y1,	 			Zo, 
				sWidth + dX, 	Y1,				-sDepth,
				
				// Bottom face
				sWidth + dX, 	Y1 - tPlate, 	-sDepth,				
				sWidth + dX, 	Y1 - tPlate, 	Zo, 
				Xo + dX, 		Y1 - tPlate, 	-sDepth,
				sWidth + dX, 	Y1 - tPlate, 	Zo, 				
				Xo + dX, 		Y1 - tPlate, 	Zo,
				Xo + dX, 		Y1 - tPlate, 	-sDepth, 




	};
		final float[] floorColorData = {
				 // Red,Green,Blue, Alpha (translucency)
				// Front face (red)
								1.0f, 0.0f, 0.0f, 1.0f,				
								1.0f, 0.0f, 0.0f, 1.0f,
								1.0f, 0.0f, 0.0f, 1.0f,
								1.0f, 0.0f, 0.0f, 1.0f,				
								1.0f, 0.0f, 0.0f, 1.0f,
								1.0f, 0.0f, 0.0f, 1.0f,
								
								// Right face (green)
								0.0f, 1.0f, 0.0f, 1.0f,				
								0.0f, 1.0f, 0.0f, 1.0f,
								0.0f, 1.0f, 0.0f, 1.0f,
								0.0f, 1.0f, 0.0f, 1.0f,				
								0.0f, 1.0f, 0.0f, 1.0f,
								0.0f, 1.0f, 0.0f, 1.0f,
								
								// Back face (blue)
								0.0f, 0.0f, 1.0f, 1.0f,				
								0.0f, 0.0f, 1.0f, 1.0f,
								0.0f, 0.0f, 1.0f, 1.0f,
								0.0f, 0.0f, 1.0f, 1.0f,				
								0.0f, 0.0f, 1.0f, 1.0f,
								0.0f, 0.0f, 1.0f, 1.0f,
								
								// Left face (yellow)
								1.0f, 1.0f, 0.0f, 1.0f,				
								1.0f, 1.0f, 0.0f, 1.0f,
								1.0f, 1.0f, 0.0f, 1.0f,
								1.0f, 1.0f, 0.0f, 1.0f,				
								1.0f, 1.0f, 0.0f, 1.0f,
								1.0f, 1.0f, 0.0f, 1.0f,
								
								// Top face (cyan)
								0.0f, 1.0f, 1.0f, 1.0f,				
								0.0f, 1.0f, 1.0f, 1.0f,
								0.0f, 1.0f, 1.0f, 1.0f,
								0.0f, 1.0f, 1.0f, 1.0f,				
								0.0f, 1.0f, 1.0f, 1.0f,
								0.0f, 1.0f, 1.0f, 1.0f,
								
								// Bottom face (magenta)
								1.0f, 0.0f, 1.0f, 1.0f,				
								1.0f, 0.0f, 1.0f, 1.0f,
								1.0f, 0.0f, 1.0f, 1.0f,
								1.0f, 0.0f, 1.0f, 1.0f,				
								1.0f, 0.0f, 1.0f, 1.0f,
				1.0f, 0.0f, 1.0f, 1.0f
	};
		final float[] floorNormalData = {0.0f, 0.0f, 1.0f,				
				0.0f, 0.0f, 1.0f,
				0.0f, 0.0f, 1.0f,
				0.0f, 0.0f, 1.0f,				
				0.0f, 0.0f, 1.0f,
				0.0f, 0.0f, 1.0f,

				// Right face 
				1.0f, 0.0f, 0.0f,				
				1.0f, 0.0f, 0.0f,
				1.0f, 0.0f, 0.0f,
				1.0f, 0.0f, 0.0f,				
				1.0f, 0.0f, 0.0f,
				1.0f, 0.0f, 0.0f,

				// Back face 
				0.0f, 0.0f, -1.0f,				
				0.0f, 0.0f, -1.0f,
				0.0f, 0.0f, -1.0f,
				0.0f, 0.0f, -1.0f,				
				0.0f, 0.0f, -1.0f,
				0.0f, 0.0f, -1.0f,

				// Left face 
				-1.0f, 0.0f, 0.0f,				
				-1.0f, 0.0f, 0.0f,
				-1.0f, 0.0f, 0.0f,
				-1.0f, 0.0f, 0.0f,				
				-1.0f, 0.0f, 0.0f,
				-1.0f, 0.0f, 0.0f,

				// Top face 
				0.0f, 1.0f, 0.0f,			
				0.0f, 1.0f, 0.0f,
				0.0f, 1.0f, 0.0f,
				0.0f, 1.0f, 0.0f,				
				0.0f, 1.0f, 0.0f,
				0.0f, 1.0f, 0.0f,

				// Bottom face 
				0.0f, -1.0f, 0.0f,			
				0.0f, -1.0f, 0.0f,
				0.0f, -1.0f, 0.0f,
				0.0f, -1.0f, 0.0f,				
				0.0f, -1.0f, 0.0f,
	0.0f, -1.0f, 0.0f
	};
		mColumnPositions = ByteBuffer.allocateDirect(floorPositionData.length * mBytesPerFloat)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();							
		mColumnPositions.put(floorPositionData).position(0);		

		mColumnColors = ByteBuffer.allocateDirect(floorColorData.length * mBytesPerFloat)
			.order(ByteOrder.nativeOrder()).asFloatBuffer();							
		mColumnColors.put(floorColorData).position(0);

		mColumnNormals = ByteBuffer.allocateDirect(floorNormalData.length * mBytesPerFloat)
			.order(ByteOrder.nativeOrder()).asFloatBuffer();							
		mColumnNormals.put(floorNormalData).position(0);
		
	}
	
	public void draw() {
		// TODO Auto-generated method stub
		mColumnPositions.position(0);		
		GLES20.glVertexAttribPointer(mPositionHandle, mPositionDataSize, GLES20.GL_FLOAT, false,
				0, mColumnPositions);        

		GLES20.glEnableVertexAttribArray(mPositionHandle);        

		// Pass in the color information
		mColumnColors.position(0);
		GLES20.glVertexAttribPointer(mColorHandle, mColorDataSize, GLES20.GL_FLOAT, false,
				0, mColumnColors);        

		GLES20.glEnableVertexAttribArray(mColorHandle);

		// Pass in the normal information
		mColumnNormals.position(0);
		GLES20.glVertexAttribPointer(mNormalHandle, mNormalDataSize, GLES20.GL_FLOAT, false, 
				0, mColumnNormals);

		GLES20.glEnableVertexAttribArray(mNormalHandle);

		// This multiplies the view matrix by the model matrix, and stores the result in the MVP matrix
		// (which currently contains model * view).
		Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);   

		// Pass in the modelview matrix.
		GLES20.glUniformMatrix4fv(mMVMatrixHandle, 1, false, mMVPMatrix, 0);                

		// This multiplies the modelview matrix by the projection matrix, and stores the result in the MVP matrix
		// (which now contains model * view * projection).
		Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);

		// Pass in the combined matrix.
		GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);

		// Draw the cube.
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 36);  
	}
}
