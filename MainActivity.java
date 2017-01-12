package com.example.classproject;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;






import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	MyGLSurfaceView mGLSurfaceView;
	MyRenderer mRenderer; 
	
	String animateStruct = "";
	String filename = "";
	public static String node = "";
	public static String orient = "";
	public static String readF = "";
	public static float force = 0;
	public static float forceValueVertical = 0;
	
	public static float forceNode3H = 0;
	public static float forceNode3V = 0;
	public static float forceNode4V = 0;
	
	// 3 MEMBER
	public static float R1v = 0; // Vertical Reaction at 1st Node
	public static double R2v = 0; // Vertical Reaction at 2nd Node
	public static double R2h = 0; // Horizontal Reaction at 2nd Node
	public static double F13 = 0; // Force in slant member
	public static double F12 = 0; // Force in Horizontal Member
	public static double F32 = 0; // Force in Vertical Member
	
	// 4 MEMBER
	public static float R1v_4 = 0; // Vertical Reaction at 1st Node
	public static double R2v_4 = 0; // Vertical Reaction at 2nd Node
	public static double R2h_4 = 0; // Horizontal Reaction at 2nd Node
	
	public static double F14_4 = 0; // Force in left Horizontal member
	public static double F23_4 = 0; // Force in Right Slant Member
	public static double F13_4 = 0; // Force in Left Slant Member
	public static double F24_4 = 0; // Force in Left Horizontal Member
	public static double F43_4 = 0; // Force in Vertical Member
	
	
	public static float length = 3;
	public static float height = 4;
	public static double slant =  5;
	public static TextView textView;
	public static TextView textView1;
	public static TextView textView2;
	public static TextView force13;
	public static TextView force12;
	public static TextView force32;
	public static TextView force42;
	public static TextView force43;
	
	public static TextView force_applied;
	public static TextView force_applied_CT;
	public static TextView force_applied_CT_V;
	public static TextView forceOnClickCompTenVNode4;
	public static TextView compression;
	public static TextView tension;
	public static TextView node1;
	public static TextView node2;
	public static TextView node3;
	public static TextView node1_4;
	public static TextView node2_4;
	public static TextView node3_4;
	public static TextView node4_4;
	public static TextView tvLayout;
	public static TextView editText1;
	public static TextView force_At_3_4member;
	public static TextView enterForceAt3;
	public static TextView force_At_4_4member;
	public static TextView enterForceAt4;
	
	public static Spinner leftRightForceHoriz3;
	public static Spinner leftRightForceVert3;
	public static Spinner sForceHoriz4;
	public static Spinner sForceVert4;
	public static Spinner sForceNode4;
		
	public static Spinner spinnerNode3; // Not in USE
	public static Spinner spinnerNode4; // Not in USE
	public static TextView forceAt3member4V;
	public static TextView enterForceAt3V;
	public static TextView forceVertical;
	public static TextView enterForceVertical;
		
	public static String buttonEvent = "";
	public static String selectTruss = "";
	public static boolean compressClick = false;
	public static boolean redoClick = false;
	public static String message = "";
	public static String messageVertical = "";
	
	public static String messageNode3H = "";
	public static String messageNode3V = "";
	public static String messageNode4V = "";
	public static boolean compTenCheck = false;
	
	public static float force1=0;
	// global variables 
	public static int vertexCount = 30;
	public static int outerVertexCount = vertexCount -1;
	public static float radius = 0.06f;
	public static float radius2 = 0.02f;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		MainActivity.node  = "Select Node";
		MainActivity.orient = "Orientation";
		MainActivity.force = 0;
		MainActivity.forceNode3H = 0;
		MainActivity.forceNode3V = 0;
		MainActivity.forceNode4V = 0;
		setContentView(R.layout.main);
				
		mGLSurfaceView = (MyGLSurfaceView)findViewById(R.id.my_gl_surface_view);
		textView = (TextView) findViewById(R.id.tvLayout2);
		textView1 = (TextView) findViewById(R.id.tvLayout2v);
		textView2 = (TextView) findViewById(R.id.tvLayout2h);
		force13 = (TextView) findViewById(R.id.tF13);
		force12 = (TextView) findViewById(R.id.tF12);
		force32 = (TextView) findViewById(R.id.tF32);
		force42 = (TextView) findViewById(R.id.tF42);
		force43 = (TextView) findViewById(R.id.tF43);
		force_applied = (TextView) findViewById(R.id.force);
		force_applied_CT = (TextView) findViewById(R.id.forceOnClickCompTen);
		force_applied_CT_V = (TextView) findViewById(R.id.forceOnClickCompTenV);
		forceOnClickCompTenVNode4 = (TextView) findViewById(R.id.forceOnClickCompTenVNode4);
		compression = (TextView) findViewById(R.id.compression);
		tension = (TextView) findViewById(R.id.tension);
		node1 = (TextView) findViewById(R.id.node1);
		node2= (TextView) findViewById(R.id.node2);
		node3 = (TextView) findViewById(R.id.node3);
		node1_4 = (TextView) findViewById(R.id.node1_4);
		node2_4= (TextView) findViewById(R.id.node2_4);
		node3_4 = (TextView) findViewById(R.id.node3_4);
		node4_4 = (TextView) findViewById(R.id.node4_4);
		tvLayout = (TextView) findViewById(R.id.tvLayout);
		editText1 = (TextView) findViewById(R.id.editText1);
		editText1 = (TextView) findViewById(R.id.editText1);
		enterForceAt3 = (TextView) findViewById(R.id.enterForceAt3);
		enterForceAt4 = (TextView) findViewById(R.id.enterForceAt4);
		tvLayout = (TextView) findViewById(R.id.tvLayout);
		force_At_3_4member = (TextView) findViewById(R.id.forceAt3member4);
		force_At_4_4member = (TextView) findViewById(R.id.force_At4member4);
		//dropdown2 = (Spinner)findViewById(R.id.spinner2);
		forceAt3member4V = (TextView) findViewById(R.id.forceAt3member4V);
		enterForceAt3V = (TextView) findViewById(R.id.enterForceAt3V);
		forceVertical = (TextView) findViewById(R.id.tvForce3MemberVertical);
		enterForceVertical = (TextView) findViewById(R.id.enterForce3MemberV);
		leftRightForceHoriz3 = (Spinner)findViewById(R.id.sForceHoriz3);
		leftRightForceVert3 = (Spinner)findViewById(R.id.sForceVert3);
		sForceHoriz4 = (Spinner)findViewById(R.id.sforceHoriz4);
		sForceVert4 = (Spinner)findViewById(R.id.sforceVert4);
		sForceNode4 = (Spinner)findViewById(R.id.sforceNode4);
		
		/*spinnerNode3= (Spinner)findViewById(R.id.spinnerNode3);
		spinnerNode4= (Spinner)findViewById(R.id.spinnerNode4);*/
		
		
		
        textView.setVisibility(View.GONE);
        textView1.setVisibility(View.GONE);
        textView2.setVisibility(View.GONE);
        force13.setVisibility(View.GONE);
        force12.setVisibility(View.GONE);
        force32.setVisibility(View.GONE);
        force42.setVisibility(View.GONE);
        force43.setVisibility(View.GONE);
        
        force_applied.setVisibility(View.GONE);
        force_applied_CT.setVisibility(View.GONE);
        force_applied_CT_V.setVisibility(View.GONE);
        forceOnClickCompTenVNode4.setVisibility(View.GONE);
        compression.setVisibility(View.GONE);
        tension.setVisibility(View.GONE);
        node1.setVisibility(View.GONE);
        node2.setVisibility(View.GONE);
        node3.setVisibility(View.GONE);
        node1_4.setVisibility(View.GONE);
        node2_4.setVisibility(View.GONE);
        node3_4.setVisibility(View.GONE);
        node4_4.setVisibility(View.GONE);
        tvLayout.setVisibility(View.GONE);
        editText1.setVisibility(View.GONE);
        force_At_3_4member.setVisibility(View.GONE);
        force_At_4_4member.setVisibility(View.GONE);
        enterForceAt3.setVisibility(View.GONE);
        enterForceAt4.setVisibility(View.GONE);
        //dropdown2.setVisibility(View.GONE);
        forceAt3member4V.setVisibility(View.GONE);
        enterForceAt3V.setVisibility(View.GONE);
        forceVertical.setVisibility(View.GONE);
        enterForceVertical.setVisibility(View.GONE);
        leftRightForceHoriz3.setVisibility(View.GONE);
        leftRightForceVert3.setVisibility(View.GONE);
        sForceHoriz4.setVisibility(View.GONE);
        sForceVert4.setVisibility(View.GONE);
        sForceNode4.setVisibility(View.GONE);
        /*spinnerNode3.setVisibility(View.GONE);
        spinnerNode4.setVisibility(View.GONE);*/

		//mGLSurfaceView.setBackgroundColor(Color.BLACK);
		final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
		final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000; 
		
		if (supportsEs2) {

			// request an OpenGL ES 2.0 capatible context
						mGLSurfaceView.setEGLContextClientVersion(2);
						final DisplayMetrics displayMetrics	= new DisplayMetrics();
						getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
						
						// set the renderer to our renderer
						mRenderer = new MyRenderer();
						mGLSurfaceView.setRenderer(mRenderer, displayMetrics.density); 

		
		}
		else {
			return;
		}
		
		
		
		String[] items2 = new String[]{"Left","Right"};
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, items2);

		String[] items3 = new String[]{"Upward","Downward"};
		ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, items3);


		MainActivity.leftRightForceHoriz3.setAdapter(adapter2);
		MainActivity.leftRightForceVert3.setAdapter(adapter3);
		MainActivity.sForceHoriz4.setAdapter(adapter2);
		MainActivity.sForceVert4.setAdapter(adapter3);
		MainActivity.sForceNode4.setAdapter(adapter3);
		
		Button bSelectTruss = (Button) findViewById(R.id.bSelectTruss);
		bSelectTruss.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				selectTruss = "3";
				 
				//Toast.makeText(getApplicationContext(), ""+force, Toast.LENGTH_LONG).show();
				// TODO Auto-generated method stub
				//Enabling visibility
				MainActivity.node1.setVisibility(View.VISIBLE);
				MainActivity.node2.setVisibility(View.VISIBLE);
				MainActivity.node3.setVisibility(View.VISIBLE);
				MainActivity.tvLayout.setVisibility(View.VISIBLE);
				MainActivity.editText1.setVisibility(View.VISIBLE);
				//MainActivity.dropdown2.setVisibility(View.VISIBLE);
				MainActivity.forceVertical.setVisibility(View.VISIBLE);
				MainActivity.enterForceVertical.setVisibility(View.VISIBLE);
				MainActivity.leftRightForceHoriz3.setVisibility(View.VISIBLE);
				MainActivity.leftRightForceVert3.setVisibility(View.VISIBLE);
				
				//Disabling visibility
				MainActivity.force_At_3_4member.setVisibility(View.GONE);
				MainActivity.force_At_4_4member.setVisibility(View.GONE);
				MainActivity.forceAt3member4V.setVisibility(View.GONE);
				MainActivity.enterForceAt3.setVisibility(View.GONE);
				MainActivity.enterForceAt4.setVisibility(View.GONE);
				MainActivity.enterForceAt3V.setVisibility(View.GONE);
				sForceHoriz4.setVisibility(View.GONE);
		        sForceVert4.setVisibility(View.GONE);
		        sForceNode4.setVisibility(View.GONE);
		        node1_4.setVisibility(View.GONE);
		        node2_4.setVisibility(View.GONE);
		        node3_4.setVisibility(View.GONE);
		        node4_4.setVisibility(View.GONE);
		        force_applied_CT_V.setVisibility(View.GONE);
		        force_applied_CT.setVisibility(View.GONE);
		        forceOnClickCompTenVNode4.setVisibility(View.GONE);
				/*MainActivity.spinnerNode3.setVisibility(View.GONE);
				MainActivity.spinnerNode4.setVisibility(View.GONE);*/
				
				
				
				
				node = "3";
				//orient = dropdown2.getSelectedItem().toString();
			}
			
		});
		
		Button bSelectTruss1 = (Button) findViewById(R.id.bSelectTruss1);
		bSelectTruss1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				selectTruss = "4";
				
				// TODO Auto-generated method stub
				// Disabling the visibility
				MainActivity.node1.setVisibility(View.GONE);
				MainActivity.node2.setVisibility(View.GONE);
				MainActivity.node3.setVisibility(View.GONE);
				MainActivity.tvLayout.setVisibility(View.GONE);
				MainActivity.editText1.setVisibility(View.GONE);
				//MainActivity.dropdown2.setVisibility(View.GONE);
				MainActivity.forceVertical.setVisibility(View.GONE);
				MainActivity.enterForceVertical.setVisibility(View.GONE);
				MainActivity.leftRightForceHoriz3.setVisibility(View.GONE);
				MainActivity.leftRightForceVert3.setVisibility(View.GONE);
				force_applied_CT_V.setVisibility(View.GONE);
				force_applied_CT.setVisibility(View.GONE);
				forceOnClickCompTenVNode4.setVisibility(View.GONE);
				// Enabling Visibility
				MainActivity.force_At_3_4member.setVisibility(View.VISIBLE);
				MainActivity.force_At_4_4member.setVisibility(View.VISIBLE);
				MainActivity.forceAt3member4V.setVisibility(View.VISIBLE);
				
				MainActivity.enterForceAt3.setVisibility(View.VISIBLE);
				MainActivity.enterForceAt4.setVisibility(View.VISIBLE);
				MainActivity.enterForceAt3V.setVisibility(View.VISIBLE);
				
				sForceHoriz4.setVisibility(View.VISIBLE);
		        sForceVert4.setVisibility(View.VISIBLE);
		        sForceNode4.setVisibility(View.VISIBLE);
		        
		        node1_4.setVisibility(View.VISIBLE);
		        node2_4.setVisibility(View.VISIBLE);
		        node3_4.setVisibility(View.VISIBLE);
		        node4_4.setVisibility(View.VISIBLE);
				/*MainActivity.spinnerNode3.setVisibility(View.VISIBLE);
				MainActivity.spinnerNode4.setVisibility(View.VISIBLE);*/
				
				
				
				//node = dropdown.getSelectedItem().toString();
				//orient = dropdown2.getSelectedItem().toString();
			}
			
		});
		
		// Reaction Button
		Button bReaction = (Button) findViewById(R.id.bReaction);
		bReaction.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				buttonEvent = "Reaction";
				System.out.println("in Reaction Button");
				// TODO Auto-generated method stub
				
				//node = dropdown.getSelectedItem().toString();
				//orient = dropdown2.getSelectedItem().toString();
				redoClick = false;
				MainActivity.compression.setVisibility(View.VISIBLE);
				MainActivity.tension.setVisibility(View.VISIBLE);
				
				if(selectTruss.equalsIgnoreCase("3"))
				{
					
					 forceOnClickCompTenVNode4.setVisibility(View.GONE);
					//Toast.makeText(getApplicationContext(), "3-H", Toast.LENGTH_LONG).show();
					EditText readForceHorizontal = (EditText)findViewById(R.id.editText1);
					 message = readForceHorizontal.getText().toString();
					System.out.println("Message " + message);
					if(message.equalsIgnoreCase(""))
					{
						force = 0;
					}
					else{
						force = Float.parseFloat(message);
					}
					
					
					EditText readForceVertical = (EditText)findViewById(R.id.enterForce3MemberV);
					 messageVertical = readForceVertical.getText().toString();
					System.out.println("Message " + messageVertical);
					
					if(messageVertical.equalsIgnoreCase(""))
					{
						forceValueVertical = 0;
					}
					else{
						forceValueVertical = Float.parseFloat(messageVertical);
					}
					
					
					// Horizontal Calculations
					System.out.println("length "+ length);
					System.out.println("height "+ height);
					System.out.println("slant "+ slant);
					
					if(MainActivity.leftRightForceHoriz3.getSelectedItem().toString().equalsIgnoreCase("Left"))
					{
						System.out.println("in 1");
						force = force;
					}
					
					if(MainActivity.leftRightForceVert3.getSelectedItem().toString().equalsIgnoreCase("Downward"))
					{
						System.out.println("in 2");
						forceValueVertical = forceValueVertical;
					}
					
					if(MainActivity.leftRightForceHoriz3.getSelectedItem().toString().equalsIgnoreCase("Right"))
					{
						System.out.println("in 3");
						force = -force;
					}
					
					if(MainActivity.leftRightForceVert3.getSelectedItem().toString().equalsIgnoreCase("Upward"))
					{
						System.out.println("in 4");
						forceValueVertical = -forceValueVertical;
					}
					
					
					
					
					System.out.println("Force H: "+ force);
					System.out.println("Force V: "+ forceValueVertical);
					
				/*	 DecimalFormat df2 = new DecimalFormat("###.##");
				        return Double.valueOf(df2.format(val));*/
					
					R1v = Math.round(((force*4)/(3))*100);
					R1v = R1v/100;
					
				    R2v = Math.round(((3*forceValueVertical-4*force)/3)*100);
				    R2v = R2v/100;
				    
					R2h = Math.round((force)*100);
					R2h = R2h/100;
					
					F13 = Math.round(((-5*R1v)/4)*100);
					F13 = F13/100;
					
					F12 = Math.round(((3*R1v)/4)*100);
					F12 = F12/100;
					
					F32 = Math.round(((-R2v))*100);
					F32 =  F32/100;
					// Reactions for horizontal FORCE
		        	 MainActivity.textView.setVisibility(View.VISIBLE);
		        	 String h = "R1(KN) = "+ R1v;
		        	 textView.setText(h);
		        	 
		        	 
		        	 MainActivity.textView1.setVisibility(View.VISIBLE);
		        	 String h1 = "R2 (KN) = "+ R2v;
		        	 textView1.setText(h1);
		        	 
		        	 
		        	 MainActivity.textView2.setVisibility(View.VISIBLE);
		        	 String h2 = "R2H (KN)= "+ R2h;
		        	 textView2.setText(h2);
		        	 
		        	 MainActivity.force13.setVisibility(View.VISIBLE);
		        	 String h3 = "F_13 (KN) = "+ F13;
		        	 force13.setText(h3);
		        	 
		        	 MainActivity.force12.setVisibility(View.VISIBLE);
		        	 String h4 = "F_12 = "+ F12;
		        	 force12.setText(h4);
		        	 
		        	 MainActivity.force32.setVisibility(View.VISIBLE);
		        	 String h5 = "F_32 (KN) = "+ F32;
		        	 force32.setText(h5);
		        	 
		        	 System.out.println("F32 : " + F32);
		        	 
		        	 if(!message.equalsIgnoreCase(""))
		        	 {
			        	 MainActivity.force_applied_CT.setVisibility(View.VISIBLE);
			        	 String h7 = "Force(KN) = "+ -force;
			        	 force_applied_CT.setText(h7);
		        	 }
		        	 if(message.equalsIgnoreCase(""))
		        	 {
			        	 MainActivity.force_applied_CT.setVisibility(View.GONE);
			        	 
		        	 }
		        	 
		        	 
		        	 if(!messageVertical.equalsIgnoreCase(""))
		        	 {
		        		 System.out.println("in vertical+++");
		        		 MainActivity.force_applied_CT_V.setVisibility(View.VISIBLE);
		        		 String h8 = "Force(KN) = "+  -forceValueVertical;
		        		 force_applied_CT_V.setText(h8);
		        	 }
		        	 if(messageVertical.equalsIgnoreCase(""))
		        	 {
		        		 System.out.println("in vertical+++");
		        		 MainActivity.force_applied_CT_V.setVisibility(View.GONE);
		        		
		        	 }
		        	/* MainActivity.force_applied.setVisibility(View.VISIBLE);
		        	 String h6 = "F_32 (KN) = "+ force;
		        	 force_applied.setText(h6);*/
		        	 System.out.println("vertical "+ forceValueVertical);
		        	 System.out.println("message vertical "+ messageVertical);
		        
			}
				
				if(MainActivity.selectTruss.equalsIgnoreCase("4"))
				{					
					//Toast.makeText(getApplicationContext(), "3-H", Toast.LENGTH_LONG).show();
									
					EditText readForceNode3H = (EditText)findViewById(R.id.enterForceAt3);
					messageNode3H = readForceNode3H.getText().toString();
					
					if(messageNode3H.equalsIgnoreCase(""))
					{
						forceNode3H = 0;
						System.out.println("Force in NULL " + forceNode3H);
					}
					else
					{
						forceNode3H = Float.parseFloat(messageNode3H);
						System.out.println("Force in else " + forceNode3H);
					}
					
					
					EditText readForceNode3V = (EditText)findViewById(R.id.enterForceAt3V);
					messageNode3V = readForceNode3V.getText().toString();
					System.out.println("Message " + messageNode3V);

					if(messageNode3V.equalsIgnoreCase(""))
					{
						forceNode3V = 0;
						System.out.println("Force in NULL " + forceNode3V);
					}
					else
					{
						forceNode3V = Float.parseFloat(messageNode3V);
						System.out.println("Force in else " + forceNode3V);
					}
					
					
					EditText readForceNode4V = (EditText)findViewById(R.id.enterForceAt4);
					messageNode4V = readForceNode4V.getText().toString();
					System.out.println("Message " + messageNode4V);
					if(messageNode4V.equalsIgnoreCase(""))
					{
						forceNode4V = 0;
					}
					else
					{
						forceNode4V = Float.parseFloat(messageNode4V);	
					}
					
					
					if(MainActivity.sForceHoriz4.getSelectedItem().toString().equalsIgnoreCase("Left"))
					{
						forceNode3H = forceNode3H;
					}
					if(MainActivity.sForceHoriz4.getSelectedItem().toString().equalsIgnoreCase("Right"))
					{
						forceNode3H = -forceNode3H;
					}
					if(MainActivity.sForceVert4.getSelectedItem().toString().equalsIgnoreCase("Downward"))
					{
						forceNode3V = forceNode3V;
					}
					if(MainActivity.sForceVert4.getSelectedItem().toString().equalsIgnoreCase("Upward"))
					{
						forceNode3V = -forceNode3V;
					}
					if(MainActivity.sForceNode4.getSelectedItem().toString().equalsIgnoreCase("Downward"))
					{
						forceNode4V = forceNode4V;
					}
					if(MainActivity.sForceNode4.getSelectedItem().toString().equalsIgnoreCase("Upward"))
					{
						forceNode4V = -forceNode4V;
					}
					
					
					// Formulae START)
					    R1v_4 = Math.round(((4*forceNode3H + 3*(forceNode3V+forceNode4V))/6)*100); // Vertical Reaction Left End
					    R1v_4 = R1v_4/100;
					    
					    R2v_4 = Math.round(((3*(forceNode3V+forceNode4V) - 4*forceNode3H)/6)*100); // Vertical Reaction Right End
					    R2v_4 = R2v_4/100;
					    
					    R2h_4 = Math.round((-forceNode3H)*100); // Horizontal Reaction Right End
					    R2h_4 = R2h_4/100;
					    
					    F13_4 = Math.round((-(5*R1v_4)/4)*100); // left slant
					    F13_4 = F13_4/100;
					    
					    F14_4 = Math.round(((3*R1v_4)/4)*100); //  left horiz
					    F14_4 = F14_4/100;
					    
					    F23_4 = Math.round((-(5*R2v_4)/4)*100); // right slant
					    F23_4 = F23_4/100;
					    
					    F24_4 = Math.round((R2h_4 + (3*R2v_4)/4)*100); // right horiz
					    F24_4 = F24_4/100;
					    
					    F43_4 = Math.round((-forceNode4V)*100);// Vertical
					    F43_4 = F43_4/100;
					// Formulae END
					    
					    // SHOWING OFF THE REACTIONS START
					    MainActivity.textView.setVisibility(View.VISIBLE);
					    String h = "R1_V = "+ R1v_4;
					    textView.setText(h);
					    
					    MainActivity.textView1.setVisibility(View.VISIBLE);
					    String h11 = "R2_V = "+ R2v_4;
					    textView1.setText(h11);
					    
					    MainActivity.textView2.setVisibility(View.VISIBLE);
			        	 String h2 = "R2H = "+ R2h_4;
			        	 textView2.setText(h2);
			        	 
			        	 MainActivity.force13.setVisibility(View.VISIBLE);
			        	 String h3 = "F_13 = " + F13_4;
			        	 force13.setText(h3);
			        	 
			        	 MainActivity.force12.setVisibility(View.VISIBLE);
			        	 String h4 = "F_14"+ '\n'+ "= "+F14_4;
			        	 force12.setText(h4);
			        	 
			        	 MainActivity.force32.setVisibility(View.VISIBLE);
			        	 String h5 = "F_32"+ '\n'+ "= "+F23_4;
			        	 force32.setText(h5);
			        	 
			        	 MainActivity.force42.setVisibility(View.VISIBLE);
			        	 String h6 = "F_42"+'\n'+ "= "+ F24_4;
			        	 force42.setText(h6);
			        	 
			        	 MainActivity.force43.setVisibility(View.VISIBLE);
			        	 String h7 = "F_43 "+ '\n'+"= "+ F43_4;
			        	 force43.setText(h7);
			        	 
			        	 if(!MainActivity.messageNode3H.equals(""))
			        	 {
				        	 MainActivity.force_applied_CT.setVisibility(View.VISIBLE);
				        	 String h8 = "Force = "+ -forceNode3H;
				        	 force_applied_CT.setText(h8);
			        	 }
			        	 
			        	 if(MainActivity.messageNode3H.equals(""))
			        	 {
				        	 MainActivity.force_applied_CT.setVisibility(View.GONE);
				        	
			        	 }
			        	 
			        	 if(!MainActivity.messageNode3V.equalsIgnoreCase(""))
			        	 {
			        		 MainActivity.force_applied_CT_V.setVisibility(View.VISIBLE);
			        		 String h9 = "Force"+ '\n'+"= "+ -forceNode3V;
			        		 force_applied_CT_V.setText(h9);
			        	 }
			        	 if(MainActivity.messageNode3V.equalsIgnoreCase(""))
			        	 {
			        		 MainActivity.force_applied_CT_V.setVisibility(View.GONE);
			        		 
			        	 }
			        	 
			        	 if(!MainActivity.messageNode4V.equalsIgnoreCase(""))
			        	 {
			        		 forceOnClickCompTenVNode4.setVisibility(View.VISIBLE);
			        		 String h9 = "Force"+ '\n'+"= "+ -forceNode4V;
			        		 forceOnClickCompTenVNode4.setText(h9);
			        	 }
			        	 if(MainActivity.messageNode4V.equalsIgnoreCase(""))
			        	 {
			        		 forceOnClickCompTenVNode4.setVisibility(View.GONE);
			        		 
			        	 }
			        	 
			        	 
			        	 // SHOWING OFF THE REACTIONS END

					    System.out.println("Reaction at A_V : "+ R1v_4);
					    System.out.println("Reaction at B_V : "+ R2v_4);
					    System.out.println("Reaction at B_H : "+ R2h_4);
					    System.out.println("F13 : "+F13_4);
					    System.out.println("F14 : "+F14_4);
					    System.out.println("F23 : "+F23_4);
					    System.out.println("F24 : "+F24_4);
					    System.out.println("F43 : "+F43_4);
					
					    
					
				}
			}
			
		}); 
		
		/* HAD TO REMOVE THIS BUTTON
		 * 
		 * 
		Button bCompTension = (Button) findViewById(R.id.bCompTension);
		bCompTension.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				compressClick = true;
				redoClick = false;
				MainActivity.compression.setVisibility(View.VISIBLE);
				MainActivity.tension.setVisibility(View.VISIBLE);
	        	 String h = "R1(KN) = "+ R1v;
	        	 textView.setText(h);
				// TODO Auto-generated method stub
				 //String message = editText.getText().toString();
				//readF = readForce.getText().toString();
				//force = Integer.parseInt(readF);
				
				 //Toast.makeText(getApplicationContext(), "Reaction at 1 = " +R1v, Toast.LENGTH_LONG).show();
				 node = dropdown.getSelectedItem().toString();
				orient = dropdown2.getSelectedItem().toString();
				EditText readForce = (EditText)findViewById(R.id.editText1);
				 message = readForce.getText().toString();
				System.out.println("Message " + message);
				force = Float.parseFloat(message);
				
				
				if (node.equalsIgnoreCase("Select Node") && orient.equalsIgnoreCase("Orientation")==false)
				{
			 Toast.makeText(getApplicationContext(), "Please Select Node", Toast.LENGTH_LONG).show();
				}
				
				if (orient.equalsIgnoreCase("Orientation") && node.equalsIgnoreCase("Select Node") == false)
				{
			 Toast.makeText(getApplicationContext(), "Please Select Orientation", Toast.LENGTH_LONG).show();
				}
				if (orient.equalsIgnoreCase("Orientation") && node.equalsIgnoreCase("Select Node"))
				{
			 Toast.makeText(getApplicationContext(), "Please Select Node and Orientation", Toast.LENGTH_LONG).show();
				}
				
				 MainActivity.force_applied_CT.setVisibility(View.VISIBLE);
	        	 String h7 = "Force(KN) = "+ force;
	        	 force_applied_CT.setText(h7);
				
			} 
		}); 
*/		Button bRedo = (Button) findViewById(R.id.bRedo);
		bRedo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Toast.makeText(getApplicationContext(), "HI", Toast.LENGTH_LONG).show();
				 selectTruss="1";
				 MainActivity.buttonEvent = "N";
				 textView.setVisibility(View.GONE);
				 textView1.setVisibility(View.GONE);
				 textView2.setVisibility(View.GONE);
				 force13.setVisibility(View.GONE);
				 force12.setVisibility(View.GONE);
				 force32.setVisibility(View.GONE);
				 force42.setVisibility(View.GONE);
				 force43.setVisibility(View.GONE);
				 force_applied.setVisibility(View.GONE);
				 force_applied_CT.setVisibility(View.GONE);
				 force_applied_CT_V.setVisibility(View.GONE);
				 forceOnClickCompTenVNode4.setVisibility(View.GONE);
				 compression.setVisibility(View.GONE);
				 tension.setVisibility(View.GONE);
				 MainActivity.node1.setVisibility(View.GONE);
				 MainActivity.node2.setVisibility(View.GONE);
				 MainActivity.node3.setVisibility(View.GONE);
				 MainActivity.node1_4.setVisibility(View.GONE);
				 MainActivity.node2_4.setVisibility(View.GONE);
				 MainActivity.node3_4.setVisibility(View.GONE);
				 MainActivity.node4_4.setVisibility(View.GONE);
				 redoClick = true;
			}
		});
		
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

}

class MyGLSurfaceView extends GLSurfaceView{
	private MyRenderer mRenderer;
	
	public MyGLSurfaceView(Context context){
		super(context);
	}
	
	public MyGLSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	public void setRenderer(MyRenderer renderer, float density) {
		mRenderer = renderer;
		super.setRenderer(renderer);
		// TODO Auto-generated method stub
	}
	
	public boolean onTouchEvent(final MotionEvent event)
	{
		queueEvent(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				float x = event.getX();
				float y = event.getY();
				float width = getWidth();
				float height = getHeight();
				float move;
				if ( x < width / 2) {
					move = -1 * ( 1 - ( x / (width / 2) ) ); 
					System.out.println("move" + move);
					mRenderer.moveFloor1(move);
				}

				if ( x > width / 2) {
					move = ( x - width / 2) / ( width / 2 );
					System.out.println("move" + move);

					mRenderer.moveFloor1(move);
				} 

			}
			
		});// close queue event
		return true;
	}// closes on Touch
	
}

class MyRenderer implements GLSurfaceView.Renderer{
	 
	
	
	
	private float[] mModelMatrix = new float[16]; // Moves models from object space 
	private float[] mViewMatrix = new float[16]; // Camera
	private float[] mProjectionMatrix = new float[16]; // projects the scene to a 2D viewport
	private float[] mMVPMatrix = new float[16]; // final matrix, passed into the shader program
	private int mMVPMatrixHandle; // passes in the transformation matrix
	private int mMVMatrixHandle; // passes in the model view matrix 
	private int mPositionHandle; // passes in the position information
	private int mColorHandle; // passes in the color information
	private int mNormalHandle; // passes in the model normal information
	private final int mBytesPerFloat = 4; // how many bytes per float
	private final int mPositionDataSize = 3; //Size of the position data in elements 
	
	//light info
	private int mLightPosHandle;
	private final Float[] mLightPosModelSpace = new Float[] {0.0f, 0.0f, 0.0f, 1.0f};
	private float [] mLightModelMatrix  = new float[16];
	//Information for the cubes
	
	private FloatBuffer mColumnPositions;
	private FloatBuffer mColumnColors;
	private FloatBuffer mColumnNormals;
	private final int mColorDataSize = 4; 
	private final int mNormalDataSize = 3;
	private int mPerVertexProgramHandle; 
	private int mPointProgramHandle;

	//Structure properties
		float tPlate = 0.075f; // thickness of the plate 
		float tColumn = 0.02f; // thickness of the column (x Axis) 
		float dColumn = 0.02f; // depth of the column (zAxis)

		float sWidth = 1.0f; // outer width of the structure (xAxis) 
		float sDepth = 1.0f; // outer depth of the structure (zAxis) 
		float floorHeight = sWidth*2; // floor height twice the width
		
		//Floor1
		float floor1Y = floorHeight; // Y coordinate // change this from 1.0f to allow for a different height to be put in 
		float floor1Z = 0.0f; // Moves Floor1 in the X,Y,Z Directions - initially set to zero		
		float Z1 = dColumn; 			
		float dX1 = 0.0f; // move the first floor initially set to zero	

		//Ground 
		float Xo = 0.0f; // can change these to give motion 
		float Yo = 1.0f; // change this value to change where the column starts 
		float Zo = 0.0f;			
	float dGx = 0.0f;

	// Position the eye in front of the origin. // We are looking toward the distance
				final float eyeX = 2.5f;					final float lookX = 0.5f;
				final float eyeY = 2.5f;					final float lookY = 1.9f;
				final float eyeZ = 2.5f;					final float lookZ = -2.0f;
			
				// Set our up vector. This is where our head would be pointing were we holding the camera.
				final float upX = 0.0f;
				final float upY = 1.0f;
				final float upZ = 0.0f;


		
		float zoom = (float) 1;
		private float heightTop;   
		
		/**
		 * DRAW COLUMNS HERE
		 */
		@Override
		public void onDrawFrame(GL10 gl) {
			GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);			        
	        GLES20.glUseProgram(mPerVertexProgramHandle);
	        float center_x=0;
	        float center_y=0;
	        float center_2x=0;
	        float center_2y=0;
	        // Roller Support Start for 3 truss elements START
	        if(MainActivity.selectTruss.equalsIgnoreCase("3"))
	        {
	        	center_x = (float) (0.52*sWidth);
	        	center_y = (float) (1.01*Yo - 1.5* tPlate-MainActivity.radius) ;
	        	 
		        drawTriangle();// FOR HINGE SUPPORT
	        }
	        if(MainActivity.selectTruss.equalsIgnoreCase("4"))
	        {
	        	center_x = (float) (0.72*sWidth);
	        	center_y = (float) (1.01*Yo - 1.5* tPlate-MainActivity.radius);
	        	
	        	center_2x = (float) (1.515*sWidth);
	        	center_2y = (float) (1.11*Yo - 1.5* tPlate-MainActivity.radius);
	        	 // FOR HINGE SUPPORT
		        drawTriangle();// FOR HINGE SUPPORT
	        }
	        
		        float rad0 = 0;
		        for (int i = 0; i < MainActivity.outerVertexCount; ++i) {
		        	float percent = (i/ (float) (MainActivity.outerVertexCount-1));
		        	float rad = (float) (percent * 2*Math.PI);
		        	
		        	
	//	        	float radius = 0.05f;
		        	float outer1X = (float) (center_x + MainActivity.radius * Math.cos(rad0));
		        	float outer1Y = (float) (center_y + MainActivity.radius * Math.sin(rad0));
		        	float outer2X = (float) (center_x + MainActivity.radius * Math.cos(rad));
		        	float outer2Y = (float) (center_y + MainActivity.radius * Math.sin(rad));
		        	
		        	float outer11X = (float) (center_2x + MainActivity.radius2 * Math.cos(rad0));
		        	float outer11Y = (float) (center_2y + MainActivity.radius2 * Math.sin(rad0));
		        	float outer22X = (float) (center_2x + MainActivity.radius2 * Math.cos(rad));
		        	float outer22Y = (float) (center_2y + MainActivity.radius2 * Math.sin(rad));
		        	
		        	drawCircle(center_x, center_y, outer1X, outer1Y, outer2X, outer2Y);
		        	drawCircle(center_2x, center_2y, outer11X, outer11Y, outer22X, outer22Y);
		        	
		        	System.out.println("outer11 x "+ outer11X);
		        	System.out.println("outer11 Y "+ outer11Y);
		        	System.out.println("outer22 x "+ outer22X);
		        	System.out.println("outer22 Y "+ outer22Y);
		        	
		        	rad0=rad;
		        }
	        // Roller Support END
		        
		       
	        
	        
	        // Roller Support Start for 3 truss elements END
	        
	        // Set program handles for cube drawing.
	        mMVPMatrixHandle = GLES20.glGetUniformLocation(mPerVertexProgramHandle, "u_MVPMatrix");
	        mMVMatrixHandle = GLES20.glGetUniformLocation(mPerVertexProgramHandle, "u_MVMatrix"); 
	        mPositionHandle = GLES20.glGetAttribLocation(mPerVertexProgramHandle, "a_Position");
	        mColorHandle = GLES20.glGetAttribLocation(mPerVertexProgramHandle, "a_Color");
	        mNormalHandle = GLES20.glGetAttribLocation(mPerVertexProgramHandle, "a_Normal"); 
	        /**
			 * DRAW COLUMNS HERE
			 */
	           
	        
	       
	        int s=  3;
	        int h = 3;
	        int v = 3;
	        int h1 = 3; // left horizontal member for 4 member
	        int h2 = 3; // right horizontal member for 4 member
	        int s1 = 3; // left slant 4 member
	        int s2 = 3; // left slant 4 member
	        int v1 = 3; // Vertical 4 member
	        
	        if(MainActivity.buttonEvent.equalsIgnoreCase("Reaction"))
	        {
	        	 drawColumn(0.1f,2.0f,0.6f,1.95f,2);// Compression
	        	 drawColumn(0.1f,1.8f,0.6f,1.75f,1);// Tension
	        }
	        
	        
	        // FOR REDO START
	        if(MainActivity.redoClick == true)
	        {
	        
	        s = 3;
	        h = 3;
	        v = 3;
	        h1 = 3; // left horizontal member for 4 member
	        h2 = 3; // right horizontal member for 4 member
	        s1 = 3; // left slant 4 member
	        s2 = 3; // left slant 4 member
	        v1 = 3; // Vertical 4 member
	        
	        }
	        // FOR REDO END
	        
	       //THREE MEMBER TRUSS START+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	       // System.out.println("F32+++++++ "+ MainActivity.F32);
	        if(MainActivity.selectTruss.equalsIgnoreCase("3"))
	        {
	        	/* */
	        	
	        	if(MainActivity.F12 < 0 && MainActivity.redoClick == false)
				{
					h = 2;
				}
	        	if(MainActivity.F12 > 0 && MainActivity.redoClick == false)
				{
					h = 1;
				}
	        	if(MainActivity.F12 == 0 && MainActivity.redoClick == false)
				{
					h = 3;
				}
	        	if(MainActivity.F13 < 0 && MainActivity.redoClick == false)
				{
					s = 2;
				}
	        	if(MainActivity.F13 > 0 && MainActivity.redoClick == false)
				{
					s = 1;
				}
	        	
	        	if(MainActivity.F13 == 0 && MainActivity.redoClick == false)
				{
					s = 3;
				}
	        	
	        	if(MainActivity.F32 < 0 && MainActivity.redoClick == false)
				{
	        		
					v = 2;
				}
	        	if(MainActivity.F32 > 0 && MainActivity.redoClick == false)
				{
					v = 1;
				}
	        	if(MainActivity.F32 == 0 && MainActivity.redoClick == false)
				{
					v = 3;
				}
	        	// TRUSS START++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				Matrix.setIdentityM(mModelMatrix, 0);
				Matrix.translateM(mModelMatrix, 0, 0.0f, 0.0f, 0.0f);
		        drawColumn(2.0f, 2.0f, 0.5f,0.9f,s); // Slant
		       	        
		       
		        Matrix.setIdentityM(mModelMatrix, 0);
				Matrix.translateM(mModelMatrix, 0, 0.0f, 0.0f, 0.0f);
		        drawColumn(2.0f,1.05f,0.5f, 0.9f,h);// Horizontal
		       	        
		        Matrix.setIdentityM(mModelMatrix, 0);
				Matrix.translateM(mModelMatrix, 0, 0.0f, 0.0f, 0.0f);
		        drawColumn(2.0f,2.0f,2.0f,0.98f,v);// vertical
		     // TRUS END++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		 		// Horizontal if the force is positive then the arrow goes to the left
		        // Vertical if the force is positive then the arrow goes downwards
				if(MainActivity.force != 0 && MainActivity.redoClick == false){
					
					if(MainActivity.force > 0 )
					{
					//Draw Horizontal Force Start
					drawColumn(2.0f,2.0f,2.5f,1.95f,3);// Horizontal Arrow
					drawColumn(2.0f,2.0f,2.15f,1.85f,3);// top side arrow
					drawColumn(2.0f,2.0f,2.15f,2.05f,3);// bottom side arrow
					//Draw Horizontal Force END
					}
					if(MainActivity.force < 0 )
					{
					//Draw Horizontal Force Start
					drawColumn(2.0f,2.0f,2.5f,1.95f,3);// Horizontal Arrow
					drawColumn(2.35f,1.85f,2.5f,1.95f,3);// top side arrow
					drawColumn(2.35f,2.15f,2.5f,1.95f,3);// bottom side arrow
					//Draw Horizontal Force END
					}
					
				}
				if(MainActivity.forceValueVertical!=0 && MainActivity.redoClick == false)
				{
					if(MainActivity.forceValueVertical>0) // downward
					{
					// Vertical  Force Start
					 drawColumn(2.0f,2.0f,2.0f,2.3f,3);// Vertical Arrow 
					drawColumn(2.0f,2.0f,2.15f,2.15f,3);// top side arrow
					drawColumn(2.0f,2.0f,1.85f,2.15f,3);// bottom side arrow
					// Vertical Force End
					}
					System.out.println("MainActivity.forceValueVertical+++ "+ MainActivity.forceValueVertical);
					if(MainActivity.forceValueVertical<0) // upward
					{
					// Vertical  Force Start
					 drawColumn(2.0f,2.0f,2.0f,2.3f,3);// Vertical Arrow 
					drawColumn(2.0f,2.36f,2.15f,2.15f,3);// top side arrow
					drawColumn(2.0f,2.36f,1.85f,2.15f,3);// bottom side arrow
					// Vertical Force End
					}
				}
				
				
					//REACTIONS START++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
					// R1v Start
				if(MainActivity.R1v <0 && MainActivity.redoClick == false )
				{
					drawColumn(0.5f,0.8f,0.5f,0.4f,3);// R1v Up Arrow
					drawColumn(0.5f,0.4f,0.35f,0.55f,3);// R1v Left Side small Arrow
					drawColumn(0.5f,0.4f,0.65f,0.55f,3);// R1v Right Side Small Arrow
					// R1v End
				}
				
				if(MainActivity.R1v >0 && MainActivity.redoClick == false )
				{
					drawColumn(0.5f,0.8f,0.5f,0.4f,3);// R1v Up Arrow
					drawColumn(0.5f,0.8f,0.35f,0.65f,3);// R1v Left Side small Arrow
					drawColumn(0.5f,0.8f,0.65f,0.65f,3);// R1v Right Side Small Arrow
					// R1v End
				}
					if(MainActivity.R2v<0 && MainActivity.redoClick == false)
					{
					// R2v Start DOWNWARDS
					drawColumn(2.0f,0.9f,2.0f,0.5f,3);// R2v Down Arrow
					drawColumn(2.0f,0.5f,1.85f,0.55f,3);// R2v Right Side small Arrow
					drawColumn(2.0f,0.5f,2.15f,0.55f,3);// R2v Left Side Small Arrow 
					// R2v End
					}
	
					if(MainActivity.R2v>0 && MainActivity.redoClick == false)
					{
					// R2v Start UPWARDS
					drawColumn(2.0f,0.9f,2.0f,0.5f,3);// R2v Down Arrow
					drawColumn(2.0f,0.9f,1.85f,0.65f,3);// R2v Right Side small Arrow
					drawColumn(2.0f,0.9f,2.15f,0.65f,3);// R2v Left Side Small Arrow 
					// R2v End
					}
					if(MainActivity.R2h > 0 && MainActivity.redoClick == false)
					{
					// R2H Start
					drawColumn(2.0f,1.0f,2.5f,0.95f,3);// R2H Horiz Arrow
					drawColumn(2.35f,1.15f,2.5f,0.95f,3);// R2H Right Side small Arrow
					drawColumn(2.35f,0.90f,2.5f,0.95f,3);// R2H Left Side Small Arrow 
					// R2H End
					}
					
					if(MainActivity.R2h < 0 && MainActivity.redoClick == false)
					{
					// R2H Start
					drawColumn(2.0f,1.05f,2.5f,1.0f,3);// R2H Horiz Arrow
					drawColumn(2.0f,1.05f,2.15f,1.15f,3);// R2H Right Side small Arrow
					drawColumn(2.0f,1.05f,2.155f,0.90f,3);// R2H Left Side Small Arrow 
					// R2H End
					}
					
					//REACTIONS END++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				}
				
	        
	       // THREE MEMBER TRUSS END+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	        
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	        
	        //FOUR MEMBER TRUSS START++++++++++++++++++++++++++++++++++++++++++++++++++++
	        if(MainActivity.selectTruss.equalsIgnoreCase("4"))
	        {
	        	
	        	
	        	// MainActivity.textView.setVisibility(View.VISIBLE);
	        /*s = 2;// Compression
	        h = 1;// Tension
	        v = 1;// Compression
	        
	        
	        
*/	        
	        Matrix.setIdentityM(mModelMatrix, 0);
			Matrix.translateM(mModelMatrix, 0, 0.0f, 0.0f, 0.0f);
			
			// color scheme for left slant start
			if(MainActivity.F13_4<0 && MainActivity.redoClick == false)
			{
				s1 = 2;
			}
			if(MainActivity.F13_4 > 0 && MainActivity.redoClick == false)
			{
				s1 = 1;
			}
			if(MainActivity.F13_4 == 0 && MainActivity.redoClick == false)
			{
				s1 = 3;
			}
			
			//color scheme for left slant END
			
			// color scheme for right slant start
			if(MainActivity.F23_4<0 && MainActivity.redoClick == false)
			{
				s2 = 2;
			}
			if(MainActivity.F23_4 > 0 && MainActivity.redoClick == false)
			{
				s2 = 1;
			}
			if(MainActivity.F23_4 == 0 && MainActivity.redoClick == false)
			{
				s2 = 3;
			}
						
			//color scheme for right slant END
			
			// color scheme for Vertical start
			if(MainActivity.F43_4 < 0 && MainActivity.redoClick == false)
			{
				v1 = 2;
			}
			if(MainActivity.F43_4 > 0 && MainActivity.redoClick == false)
			{
				v1 = 1;
			}
			if(MainActivity.F43_4 == 0 && MainActivity.redoClick == false)
			{
				v1 = 3;
			}
						
			//color scheme for Vertical END
			
			// color scheme for left Horizontal start
			if(MainActivity.F14_4 < 0 && MainActivity.redoClick == false)
			{
				h1 = 2;
			}
			if(MainActivity.F14_4 > 0 && MainActivity.redoClick == false)
			{
				h1 = 1;
			}
			if(MainActivity.F14_4 == 0 && MainActivity.redoClick == false)
			{
				h1 = 3;
			}
						
			//color scheme for left Horizontal END
			
			// color scheme for left Horizontal start
			if(MainActivity.F24_4 < 0 && MainActivity.redoClick == false)
			{
				h2 = 2;
			}
			if(MainActivity.F24_4 > 0 && MainActivity.redoClick == false)
			{
				h2 = 1;
			}
			if(MainActivity.F24_4 == 0 && MainActivity.redoClick == false)
			{
				h2 = 3;
			}
						
			//color scheme for left Horizontal END
						
			if (MainActivity.forceNode3H != 0 && MainActivity.redoClick == false) 
			{
				if (MainActivity.forceNode3H > 0) // towards left
				{
					// Horizontal Force Start
					drawColumn(1.6f,2.0f,2.1f,1.95f,3);// Horizontal Arrow
					drawColumn(1.6f,2.0f,1.75f,2.08f,3);
					drawColumn(1.6f,2.0f,1.75f,1.85f,3);
					// Horizontal Force End
				}
				if (MainActivity.forceNode3H < 0) // towards Right
				{
					// Horizontal Force Start
					drawColumn(1.6f,2.0f,2.1f,1.95f,3);// Horizontal Arrow
					drawColumn(1.95f,2.08f,2.1f,1.95f,3);
					drawColumn(1.95f,1.9f,2.1f,1.95f,3);
					// Horizontal Force End
				}
			}
			
			if (MainActivity.forceNode3V !=0 && MainActivity.redoClick == false)
			{
				if (MainActivity.forceNode3V > 0)// downward
				{
					// vertical Force Start
					drawColumn(1.5f,2.0f,1.5f,2.35f,3);// Vertical at Node 3 Arrow
					drawColumn(1.5f,2.0f,1.35f,2.15f,3);
					drawColumn(1.5f,2.0f,1.65f,2.15f,3);
			// vertical Force End
				}
				if (MainActivity.forceNode3V < 0)// Upward
				{
					// vertical Force Start
					drawColumn(1.5f,2.0f,1.5f,2.35f,3);// Vertical at Node 3 Arrow
					drawColumn(1.5f,2.45f,1.35f,2.25f,3);
					drawColumn(1.5f,2.45f,1.65f,2.25f,3);
			// vertical Force End
				}
			}
			if(MainActivity.forceNode4V !=0 && MainActivity.redoClick == false)
			{
				if(MainActivity.forceNode4V > 0)
				{
					drawColumn(1.50f,0.93f,1.5f,0.43f,3);// Vertical at node 4 Arrow
					drawColumn(1.50f,0.43f,1.40f,0.60f,3);
					drawColumn(1.50f,0.43f,1.6f,0.60f,3);
				}
				if(MainActivity.forceNode4V < 0)
				{
					drawColumn(1.50f,0.93f,1.5f,0.43f,3);// Vertical at node 4 Arrow
					drawColumn(1.50f,0.93f,1.4f,0.75f,3);
					drawColumn(1.50f,0.93f,1.6f,0.75f,3);
				}
			}
			
			
			// TRUSS START
			Matrix.setIdentityM(mModelMatrix, 0);
			Matrix.translateM(mModelMatrix, 0, 0.0f, 0.0f, 0.0f);
	        drawColumn(1.5f, 2.0f, 0.7f,0.90f,s1); // Slant1 - left
	       	        
	       
	        Matrix.setIdentityM(mModelMatrix, 0);
			Matrix.translateM(mModelMatrix, 0, 0.0f, 0.0f, 0.0f);
	        drawColumn(1.5f, 1.015f,0.7f,0.9f,h1);// Horizontal left
	        
	        Matrix.setIdentityM(mModelMatrix, 0);
			Matrix.translateM(mModelMatrix, 0, 0.0f, 0.0f, 0.0f);
	        drawColumn(2.3f,1.045f,1.5f, 0.93f,h2);// Horizontal right
	       	        
	        Matrix.setIdentityM(mModelMatrix, 0);
			Matrix.translateM(mModelMatrix, 0, 0.0f, 0.0f, 0.0f);
	        drawColumn(1.5f,2.0f,2.3f,0.98f,s2);// slant2 - right
	        
	        Matrix.setIdentityM(mModelMatrix, 0);
			Matrix.translateM(mModelMatrix, 0, 0.0f, 0.0f, 0.0f);
	        drawColumn(1.5f,2.0f,1.5f,0.945f,v1);// vertical
	        	        
	     // TRUS END
	        
	        // Reactions START
	        if(MainActivity.R2v_4 >0 && MainActivity.redoClick == false)
	        {
	        // Vertically UPWARDS at B or at NODE no 2 START
	        drawColumn(2.3f,0.9f,2.3f,0.5f,3);// Vertical Arrow 
			drawColumn(2.3f,0.9f,2.15f,0.7f,3);// top side arrow
			drawColumn(2.3f,0.9f,2.45f,0.7f,3);// bottom side arrow
			// Vertically UPWARDS at B or at NODE no 2 END
	        }
	        if(MainActivity.R2v_4 <0 && MainActivity.redoClick == false)
	        {
			 // Vertically DOWNWARDS at B or at NODE no 2 START
	        drawColumn(2.3f,0.9f,2.3f,0.5f,3);// Vertical Arrow 
			drawColumn(2.3f,0.5f,2.15f,0.55f,3);// top side arrow
			drawColumn(2.3f,0.5f,2.45f,0.55f,3);// bottom side arrow
			// Vertically UPWARDS at B or at NODE no 2 END
	        }
	        if(MainActivity.R1v_4 >0 && MainActivity.redoClick == false)
	        {
			   // Vertically UPWARDS at A or at NODE no 1 START
	        drawColumn(0.72f,0.8f,0.72f,0.4f,3);// Vertical Arrow 
			drawColumn(0.72f,0.8f,0.57f,0.6f,3);// top side arrow
			drawColumn(0.72f,0.8f,0.87f,0.6f,3);// bottom side arrow
			// Vertically UPWARDS at A or at NODE no 1 END
	        }
	        
	        if(MainActivity.R1v_4 <0 && MainActivity.redoClick == false)
	        {
			   // Vertically UPWARDS at A or at NODE no 1 START
	        drawColumn(0.72f,0.8f,0.72f,0.4f,3);// Vertical Arrow 
			drawColumn(0.72f,0.4f,0.57f,0.6f,3);// top side arrow
			drawColumn(0.72f,0.4f,0.87f,0.6f,3);// bottom side arrow
			// Vertically UPWARDS at A or at NODE no 1 END
	        }
	        
	        if(MainActivity.R2h_4 <0 && MainActivity.redoClick == false)
	        {
			// HORIZONTAL AT B or NODE 2 START
			drawColumn(2.35f,1.05f,2.65f,0.99f,3);// R2H Horiz Arrow
			drawColumn(2.50f,1f,2.65f,0.99f,3);// R2H Right Side small Arrow
			drawColumn(2.50f,1.1f,2.65f,0.99f,3);// R2H Left Side Small Arrow 
			// HORIZONTAL AT B or NODE 2 END
	        }
			
			//Compression/Tensionn
	        /*if(MainActivity.buttonEvent.equalsIgnoreCase("Reaction"))
	        {
	        	 drawColumn(0.1f,2.0f,0.6f,1.95f,2);// Compression
	        	 drawColumn(0.1f,1.8f,0.6f,1.75f,1);// Tension
	        }*/
	        // Reactions END
			
				
	        }
	        // FOUR MEMBER TRUSS END++++++++++++++++++++++++++++++++++++++++++++++++++++
	        
	       
			
		}
		private void drawTriangle(/*float centerX, float centerY, float outer1X, float outer1Y, float outer2X, float outer2Y*/)
		{
			float [] drawtrianglePositionData={};
			if(MainActivity.selectTruss.equalsIgnoreCase("3"))
			{
	
				drawtrianglePositionData = new float[]{
					
						/*centerX, centerY, 0.0f,		//0		
						outer1X, outer1Y, 0.0f,		//1
						outer2X, outer2Y, 0.0f, 	//2
	*/					
						1.85f, 0.87f, 0f, // left vertice
						 2.15f, 0.88f, 0f, // right vertice
						 2.01f, 0.98f, 0f 	// vertex
				};
			}
			
			if(MainActivity.selectTruss.equalsIgnoreCase("4"))
			{
	
				drawtrianglePositionData = new float[]{
					
						/*centerX, centerY, 0.0f,		//0		
						outer1X, outer1Y, 0.0f,		//1
						outer2X, outer2Y, 0.0f, 	//2
	*/					
						 
						 2.15f, 0.87f, 0f, // left  vertice
						 2.45f, 0.88f, 0f, // right vertice
						 2.315f, 0.98f, 0f 	// vertex
				};
			}
			float triangleColorData[] = new float[] { 1.0f, 0.0f, 0.0f, 0.0f};

			float triangleNormalData[] = new float[] {1.0f, 1.0f, 1.0f, 0.0f};

			// Initialize the buffers.
			mColumnPositions = ByteBuffer.allocateDirect(drawtrianglePositionData.length * mBytesPerFloat)
		    .order(ByteOrder.nativeOrder()).asFloatBuffer();							
			mColumnPositions.put(drawtrianglePositionData).position(0);		

			mColumnColors = ByteBuffer.allocateDirect(triangleColorData.length * mBytesPerFloat)
		    .order(ByteOrder.nativeOrder()).asFloatBuffer();							
			mColumnColors.put(triangleColorData).position(0);

			mColumnNormals = ByteBuffer.allocateDirect(triangleNormalData.length * mBytesPerFloat)
		    .order(ByteOrder.nativeOrder()).asFloatBuffer();							
			mColumnNormals.put(triangleNormalData).position(0); 
			
				// Pass in the position information
							mColumnPositions.position(0);		
							GLES20.glVertexAttribPointer(0, 3, GLES20.GL_FLOAT, false,
									0, mColumnPositions);        
				        
							GLES20.glEnableVertexAttribArray(mPositionHandle);        
							// Pass in the normal information
							mColumnNormals.position(0);
							GLES20.glVertexAttribPointer(mNormalHandle, mNormalDataSize, GLES20.GL_FLOAT, false, 
									0, mColumnNormals);

							GLES20.glEnableVertexAttribArray(0);
							// Draw the triangle
						    GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
	
		}
		private void drawCircle(float centerX, float centerY, float outer1X, float outer1Y, float outer2X, float outer2Y) {
			
			
			float [] drawtrianglePositionData = {
				
					centerX, centerY, 0.0f,		//0		
					outer1X, outer1Y, 0.0f,		//1
					outer2X, outer2Y, 0.0f, 	//2
					
			};
			
			float triangleColorData[] = new float[] { 0.0f, 0.0f, 1.0f, 1.0f};

			float triangleNormalData[] = new float[] {0.0f, 0.0f, 1.0f, 1.0f};

			// Initialize the buffers.
			mColumnPositions = ByteBuffer.allocateDirect(drawtrianglePositionData.length * mBytesPerFloat)
		    .order(ByteOrder.nativeOrder()).asFloatBuffer();							
			mColumnPositions.put(drawtrianglePositionData).position(0);		

			mColumnColors = ByteBuffer.allocateDirect(triangleColorData.length * mBytesPerFloat)
		    .order(ByteOrder.nativeOrder()).asFloatBuffer();							
			mColumnColors.put(triangleColorData).position(0);

			mColumnNormals = ByteBuffer.allocateDirect(triangleNormalData.length * mBytesPerFloat)
		    .order(ByteOrder.nativeOrder()).asFloatBuffer();							
			mColumnNormals.put(triangleNormalData).position(0); 
			
				// Pass in the position information
							mColumnPositions.position(0);		
							GLES20.glVertexAttribPointer(0, 3, GLES20.GL_FLOAT, false,
									0, mColumnPositions);        
				        
							GLES20.glEnableVertexAttribArray(mPositionHandle);        
							// Pass in the normal information
							mColumnNormals.position(0);
							GLES20.glVertexAttribPointer(mNormalHandle, mNormalDataSize, GLES20.GL_FLOAT, false, 
									0, mColumnNormals);

							GLES20.glEnableVertexAttribArray(0);
							// Draw the triangle
						    GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
			}

		
		private void drawColumn(float topPointX, float topPointY, float bottomPointX, float bottomPointY, int color){
			// dXb top floor movement
			// dxa bottom floor movement
			float Y1=topPointY; // top floor position
			float Yo=bottomPointY; // bottom floor position \
			float dXb = 0;
			float dXa = 0;
			final float[] columnPositionData ={
					// Front face
					topPointX + dXb, 						Y1 - tPlate, 	Zo,		//1		
					bottomPointX + dXa, 					Yo, 			Zo,		//2
					topPointX + tColumn + dXb, 				Y1 - tPlate, 	Zo, 	//3
					bottomPointX + dXa, 					Yo, 			Zo, 	//2
					bottomPointX + tColumn + dXa, 			Yo, 			Zo,		//4
					topPointX + tColumn + dXb, 				Y1 - tPlate, 	Zo,		//3

					// Right face
					topPointX + tColumn + dXb,				Y1 - tPlate, 	Zo,				
					bottomPointX + tColumn + dXa, 			Yo, 			Zo,
					topPointX + tColumn + dXb, 				Y1 - tPlate, 	-Z1,
					bottomPointX + tColumn + dXa, 			Yo, 			Zo,				
					bottomPointX + tColumn + dXa, 			Yo, 			-Z1,
					topPointX + tColumn + dXb, 				Y1 - tPlate, 	-Z1,

					// Back face
					topPointX + tColumn + dXb, 				Y1 - tPlate, 	-Z1,				
					bottomPointX + tColumn + dXa, 			Yo, 	-Z1,
					topPointX + dXb, 						Y1 - tPlate, 	-Z1,
					bottomPointX + tColumn + dXa,			Yo, 	-Z1,				
					bottomPointX + dXa, 					Yo, 	-Z1,
					topPointX + dXb, 						Y1 - tPlate, 	-Z1,

					// Left face
					topPointX + dXb, 						Y1 - tPlate, 	-Z1,				
					bottomPointX + dXa, 					Yo, 			-Z1,
					topPointX + dXb, 						Y1 - tPlate, 	Zo, 
					bottomPointX + dXa, 					Yo, 			-Z1,				
					bottomPointX + dXa, 					Yo, 			Zo, 
					topPointX + dXb, 						Y1 - tPlate, 	Zo, 

					// Top face
					topPointX + dXb, 						Y1 - tPlate, 	-Z1,				
					topPointX + dXb, 						Y1 - tPlate, 	Zo, 
					topPointX + tColumn + dXb, 				Y1 - tPlate, 	-Z1, 
					topPointX + dXb, 						Y1 - tPlate, 	Zo, 				
					topPointX + tColumn + dXb, 				Y1 - tPlate,	 Zo, 
					topPointX + tColumn + dXb, 				Y1 - tPlate,	-Z1,

					// Bottom face
					bottomPointX + tColumn + dXa, 			Yo, 	-Z1,				
					bottomPointX + tColumn + dXa, 			Yo, 	Zo, 
					bottomPointX + dXa, 					Yo, 	-Z1,
					bottomPointX + tColumn + dXa, 			Yo, 	Zo, 				
					bottomPointX + dXa, 					Yo, 	Zo,
					bottomPointX + dXa, 					Yo, 	-Z1,

			};
			
				final float[] columnColorData_Red = {
						// Front face (red)
						1.0f, 0.0f, 0.0f, 1.0f,				
						1.0f, 0.0f, 0.0f, 1.0f,
						1.0f, 0.0f, 0.0f, 1.0f,
						1.0f, 0.0f, 0.0f, 1.0f,				
						1.0f, 0.0f, 0.0f, 1.0f,
						1.0f, 0.0f, 0.0f, 1.0f,

						// Right face (green)
						1.0f, 0.0f, 0.0f, 1.0f,				
						1.0f, 0.0f, 0.0f, 1.0f,
						1.0f, 0.0f, 0.0f, 1.0f,
						1.0f, 0.0f, 0.0f, 1.0f,				
						1.0f, 0.0f, 0.0f, 1.0f,
						1.0f, 0.0f, 0.0f, 1.0f,

						// Back face (blue)
						1.0f, 0.0f, 0.0f, 1.0f,				
						1.0f, 0.0f, 0.0f, 1.0f,
						1.0f, 0.0f, 0.0f, 1.0f,
						1.0f, 0.0f, 0.0f, 1.0f,				
						1.0f, 0.0f, 0.0f, 1.0f,
						1.0f, 0.0f, 0.0f, 1.0f,

						// Left face (yellow)
						1.0f, 0.0f, 0.0f, 1.0f,				
						1.0f, 0.0f, 0.0f, 1.0f,
						1.0f, 0.0f, 0.0f, 1.0f,
						1.0f, 0.0f, 0.0f, 1.0f,				
						1.0f, 0.0f, 0.0f, 1.0f,
						1.0f, 0.0f, 0.0f, 1.0f,

						// Top face (cyan)
						1.0f, 0.0f, 0.0f, 1.0f,				
						1.0f, 0.0f, 0.0f, 1.0f,
						1.0f, 0.0f, 0.0f, 1.0f,
						1.0f, 0.0f, 0.0f, 1.0f,				
						1.0f, 0.0f, 0.0f, 1.0f,
						1.0f, 0.0f, 0.0f, 1.0f,

						// Bottom face (magenta)
						1.0f, 0.0f, 0.0f, 1.0f,				
						1.0f, 0.0f, 0.0f, 1.0f,
						1.0f, 0.0f, 0.0f, 1.0f,
						1.0f, 0.0f, 0.0f, 1.0f,				
						1.0f, 0.0f, 0.0f, 1.0f,
						1.0f, 0.0f, 0.0f, 1.0f,

				};
		
			
				final float[] columnColorData_Green = {
						// Front face (red)
						0.0f, 1.0f, 0.0f, 1.0f,				
						0.0f, 1.0f, 0.0f, 1.0f,
						0.0f, 1.0f, 0.0f, 1.0f,
						0.0f, 1.0f, 0.0f, 1.0f,				
						0.0f, 1.0f, 0.0f, 1.0f,
						0.0f, 1.0f, 0.0f, 1.0f,

						// Right face (green)
						0.0f, 1.0f, 0.0f, 1.0f,				
						0.0f, 1.0f, 0.0f, 1.0f,
						0.0f, 1.0f, 0.0f, 1.0f,
						0.0f, 1.0f, 0.0f, 1.0f,				
						0.0f, 1.0f, 0.0f, 1.0f,
						0.0f, 1.0f, 0.0f, 1.0f,
						
						// Back face (blue)
						0.0f, 1.0f, 0.0f, 1.0f,				
						0.0f, 1.0f, 0.0f, 1.0f,
						0.0f, 1.0f, 0.0f, 1.0f,
						0.0f, 1.0f, 0.0f, 1.0f,				
						0.0f, 1.0f, 0.0f, 1.0f,
						0.0f, 1.0f, 0.0f, 1.0f,
						
						// Left face (yellow)
						0.0f, 1.0f, 0.0f, 1.0f,				
						0.0f, 1.0f, 0.0f, 1.0f,
						0.0f, 1.0f, 0.0f, 1.0f,
						0.0f, 1.0f, 0.0f, 1.0f,				
						0.0f, 1.0f, 0.0f, 1.0f,
						0.0f, 1.0f, 0.0f, 1.0f,
					
						// Top face (cyan)
						0.0f, 1.0f, 0.0f, 1.0f,				
						0.0f, 1.0f, 0.0f, 1.0f,
						0.0f, 1.0f, 0.0f, 1.0f,
						0.0f, 1.0f, 0.0f, 1.0f,				
						0.0f, 1.0f, 0.0f, 1.0f,
						0.0f, 1.0f, 0.0f, 1.0f,

						// Bottom face (magenta)
						0.0f, 1.0f, 0.0f, 1.0f,				
						0.0f, 1.0f, 0.0f, 1.0f,
						0.0f, 1.0f, 0.0f, 1.0f,
						0.0f, 1.0f, 0.0f, 1.0f,				
						0.0f, 1.0f, 0.0f, 1.0f,
						0.0f, 1.0f, 0.0f, 1.0f,

				};		
			
			
				final float[] columnColorData_Blue = {
						// Front face (red)
						0.0f, 0.0f, 1.0f, 1.0f,				
						0.0f, 0.0f, 1.0f, 1.0f,
						0.0f, 0.0f, 1.0f, 1.0f,
						0.0f, 0.0f, 1.0f, 1.0f,				
						0.0f, 0.0f, 1.0f, 1.0f,
						0.0f, 0.0f, 1.0f, 1.0f,

						// Right face (green)
						0.0f, 0.0f, 1.0f, 1.0f,				
						0.0f, 0.0f, 1.0f, 1.0f,
						0.0f, 0.0f, 1.0f, 1.0f,
						0.0f, 0.0f, 1.0f, 1.0f,				
						0.0f, 0.0f, 1.0f, 1.0f,
						0.0f, 0.0f, 1.0f, 1.0f,

						// Back face (blue)
						0.0f, 0.0f, 1.0f, 1.0f,				
						0.0f, 0.0f, 1.0f, 1.0f,
						0.0f, 0.0f, 1.0f, 1.0f,
						0.0f, 0.0f, 1.0f, 1.0f,				
						0.0f, 0.0f, 1.0f, 1.0f,
						0.0f, 0.0f, 1.0f, 1.0f,

						// Left face (yellow)
						0.0f, 0.0f, 1.0f, 1.0f,				
						0.0f, 0.0f, 1.0f, 1.0f,
						0.0f, 0.0f, 1.0f, 1.0f,
						0.0f, 0.0f, 1.0f, 1.0f,				
						0.0f, 0.0f, 1.0f, 1.0f,
						0.0f, 0.0f, 1.0f, 1.0f,

						// Top face (cyan)
						0.0f, 0.0f, 1.0f, 1.0f,				
						0.0f, 0.0f, 1.0f, 1.0f,
						0.0f, 0.0f, 1.0f, 1.0f,
						0.0f, 0.0f, 1.0f, 1.0f,				
						0.0f, 0.0f, 1.0f, 1.0f,
						0.0f, 0.0f, 1.0f, 1.0f,

						// Bottom face (magenta)
						0.0f, 0.0f, 1.0f, 1.0f,				
						0.0f, 0.0f, 1.0f, 1.0f,
						0.0f, 0.0f, 1.0f, 1.0f,
						0.0f, 0.0f, 1.0f, 1.0f,				
						0.0f, 0.0f, 1.0f, 1.0f,
						0.0f, 0.0f, 1.0f, 1.0f,

				};
			
			
			final float[] columnNormalData = {
					// Front face
					0.0f, 0.0f, 1.0f,				
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

			if (color==1)// Red
			{
				
				mColumnColors = ByteBuffer.allocateDirect(columnColorData_Red.length * mBytesPerFloat)
						.order(ByteOrder.nativeOrder()).asFloatBuffer();							
				mColumnColors.put(columnColorData_Red).position(0);
			}
			
			if (color==2)// Green
			{
				
				mColumnColors = ByteBuffer.allocateDirect(columnColorData_Green.length * mBytesPerFloat)
						.order(ByteOrder.nativeOrder()).asFloatBuffer();							
				mColumnColors.put(columnColorData_Green).position(0);
			}
			if (color==3)// Blue
			{
				
				mColumnColors = ByteBuffer.allocateDirect(columnColorData_Blue.length * mBytesPerFloat)
						.order(ByteOrder.nativeOrder()).asFloatBuffer();							
				mColumnColors.put(columnColorData_Blue).position(0);
			}
			
			
			
			// Initialize the buffers.
			mColumnPositions = ByteBuffer.allocateDirect(columnPositionData.length * mBytesPerFloat)
					.order(ByteOrder.nativeOrder()).asFloatBuffer();							
			mColumnPositions.put(columnPositionData).position(0);		

			

			mColumnNormals = ByteBuffer.allocateDirect(columnNormalData.length * mBytesPerFloat)
					.order(ByteOrder.nativeOrder()).asFloatBuffer();							
			mColumnNormals.put(columnNormalData).position(0); 
			drawCube();

			}
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub
		GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		// Use culling to remove back faces.
		GLES20.glEnable(GLES20.GL_CULL_FACE);

		// Enable depth testing
		GLES20.glEnable(GLES20.GL_DEPTH_TEST); 

		// Set the view matrix. This matrix can be said to represent the camera position.
		// NOTE: In OpenGL 1, a ModelView matrix is used, which is a combination of a model and
		// view matrix. In OpenGL 2, we can keep track of these matrices separately if we choose.
		Matrix.setLookAtM(mViewMatrix, 0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);		

		final String vertexShader = getVertexShader();   		
		final String fragmentShader = getFragmentShader();			

		final int vertexShaderHandle = compileShader(GLES20.GL_VERTEX_SHADER, vertexShader);		
		final int fragmentShaderHandle = compileShader(GLES20.GL_FRAGMENT_SHADER, fragmentShader);		

		mPerVertexProgramHandle = createAndLinkProgram(vertexShaderHandle, fragmentShaderHandle, 
				new String[] {"a_Position",  "a_Color", "a_Normal"});								                                							       

		// Define a simple shader program for our point.
		final String pointVertexShader =
				"uniform mat4 u_MVPMatrix;      \n"		
						+	"attribute vec4 a_Position;     \n"		
						+ "void main()                    \n"
						+ "{                              \n"
						+ "   gl_Position = u_MVPMatrix   \n"
						+ "               * a_Position;   \n"
						+ "   gl_PointSize = 5.0;         \n"
						+ "}                              \n";

		final String pointFragmentShader = 
				"precision mediump float;       \n"					          
						+ "void main()                    \n"
						+ "{                              \n"
						+ "   gl_FragColor = vec4(1.0,    \n" 
						+ "   1.0, 1.0, 1.0);             \n"
						+ "}                              \n";

		final int pointVertexShaderHandle = compileShader(GLES20.GL_VERTEX_SHADER, pointVertexShader);
		final int pointFragmentShaderHandle = compileShader(GLES20.GL_FRAGMENT_SHADER, pointFragmentShader);
		mPointProgramHandle = createAndLinkProgram(pointVertexShaderHandle, pointFragmentShaderHandle, 
				new String[] {"a_Position"});   
		
		 //mTriangle = new Triangle();

	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		GLES20.glViewport(0, 0, width, height);
		// Create a new perspective projection matrix. The height will stay the same
		// while the width will vary as per aspect ratio.
		final float ratio = (float) width / height * 2;
		final float left = -ratio * zoom;
		final float right = ratio * zoom;
		final float bottom = -2.0f * zoom;
		final float top = 2.0f * zoom;
		final float near = 0.5f;
		final float far = 20.0f; 	
		Matrix.orthoM(mProjectionMatrix, 0, left, right, bottom, top, near, far);
//		Matrix.frustumM(mProjectionMatrix, 0, left, right, bottom, top, near, far);

		// TODO Auto-generated method stub
		
	}
	public void setdX1(float dX1) {
		this.dX1 = dX1;
	}
	
	public void moveGround(float f){
		dGx = f;
	}
public void moveFloor1(float f){
	dX1 = f;
	}
	
	private void drawLevel(float floorBot, float floorTop, float dXa, float dXb) {
		//float floorBot; float floorTop; float dXa; float dXb;
//		float tRight = sWidth - tColumn; 
//		float tBack = -sDepth + tColumn; 
//		Matrix.setIdentityM(mModelMatrix, 0);
//		Matrix.translateM(mModelMatrix, 0, 0.0f, 0.0f, 0.0f);
//		drawColumn(floorTop , floorBot, dXa, dXb);
//
//		Matrix.setIdentityM(mModelMatrix, 0);
//		Matrix.translateM(mModelMatrix, 0, tRight, 0.0f, 0.0f);
//		drawColumn(floorTop , floorBot, dXa, dXb);
//
//		// Back Left Column                 
//		Matrix.setIdentityM(mModelMatrix, 0);
//		Matrix.translateM(mModelMatrix, 0, 0.0f, 0.0f, tBack);
//		drawColumn(floorTop , floorBot, dXa, dXb);
//
//		// Back right Column 
//		Matrix.setIdentityM(mModelMatrix, 0);
//		Matrix.translateM(mModelMatrix, 0, tRight, 0.0f, tBack); 
//		drawColumn(floorTop , floorBot, dXa, dXb); 

		//floor 1 - Draws floor1
//        Matrix.setIdentityM(mModelMatrix, 0);
//        Matrix.translateM(mModelMatrix, 0, 0, 0, 0);
//        drawFloor(floorTop, dXb);   

		
	} 
	
	 private void drawGround(float dGx) {
	
		 final float[] groundPositionData = {// Front face
					(float) (Xo - 0.5* sWidth) + dGx, 									Yo, 			Zo,		//1		
					(float) (Xo - 0.5* sWidth) + dGx, 			(float) (Yo - 1.5* tPlate), 			Zo,		//2
					(float) (1.5*sWidth) + dGx, 										Yo, 			Zo, 	//3
					(float) (Xo - 0.5* sWidth) + dGx, 			(float) (Yo - 1.5* tPlate), 			Zo, 	//2
					(float) (1.5*sWidth) + dGx, 				(float) (Yo - 1.5* tPlate), 			Zo,		//4
					(float) (1.5*sWidth) + dGx, 										Yo, 			Zo,		//3

					// Right face
					(float) (1.5*sWidth) + dGx,										Yo, 				Zo,				
					(float) (1.5*sWidth) + dGx, 			(float) (Yo - 1.5* tPlate), 				Zo,
					(float) (1.5*sWidth) + dGx, 									Yo, 			-sDepth,
					(float) (1.5*sWidth) + dGx, 			(float) (Yo - 1.5* tPlate), 				Zo,				
					(float) (1.5*sWidth) + dGx, 			(float) (Yo - 1.5* tPlate), 			-sDepth,
					(float) (1.5*sWidth) + dGx, 									Yo, 			-sDepth,

					// Back face
					(float) (1.5*sWidth) + dGx, 									Yo, 			-sDepth,				
					(float) (1.5*sWidth) + dGx, 			(float) (Yo - 1.5* tPlate), 			-sDepth,
					(float) (Xo - 0.5* sWidth) + dGx, 								Yo, 			-sDepth,
					(float) (1.5*sWidth) + dGx,				(float) (Yo - 1.5* tPlate), 			-sDepth,				
					(float) (Xo - 0.5* sWidth) + dGx, 		(float) (Yo - 1.5* tPlate), 			-sDepth,
					(float) (Xo - 0.5* sWidth) + dGx, 								Yo, 			-sDepth,

					// Left face
					(float) (Xo - 0.5* sWidth) + dGx, 							Yo, 				-sDepth,				
					(float) (Xo - 0.5* sWidth) + dGx, 	(float) (Yo - 1.5* tPlate), 				-sDepth,
					(float) (Xo - 0.5* sWidth) + dGx, 							Yo, 					Zo, 
					(float) (Xo - 0.5* sWidth) + dGx, 	(float) (Yo - 1.5* tPlate), 				-sDepth,				
					(float) (Xo - 0.5* sWidth) + dGx, 	(float) (Yo - 1.5* tPlate), 					Zo, 
					(float) (Xo - 0.5* sWidth) + dGx, 							Yo, 					Zo, 

					// Top face
					(float) (Xo - 0.5* sWidth) + dGx, 								Yo, 			-sDepth,				
					(float) (Xo - 0.5* sWidth) + dGx, 								Yo, 				Zo, 
					(float) (1.5*sWidth) + dGx, 									Yo, 			-sDepth, 
					(float) (Xo - 0.5* sWidth) + dGx, 								Yo, 				Zo, 				
					(float) (1.5*sWidth) + dGx, 									Yo,	 				Zo, 
					(float) (1.5*sWidth) + dGx, 									Yo,				-sDepth,
					
					// Bottom face
					(float) (1.5*sWidth) + dGx, 			(float) (Yo - 1.5* tPlate), 			-sDepth,				
					(float) (1.5*sWidth) + dGx, 			(float) (Yo - 1.5* tPlate), 				Zo, 
					(float) (Xo - 0.5* sWidth) + dGx, 		(float) (Yo - 1.5* tPlate), 			-sDepth,
					(float) (1.5*sWidth) + dGx, 			(float) (Yo - 1.5* tPlate), 				Zo, 				
					(float) (Xo - 0.5* sWidth) + dGx, 		(float) (Yo - 1.5* tPlate), 				Zo,
					(float) (Xo - 0.5* sWidth) + dGx, 		(float) (Yo - 1.5* tPlate), 			-sDepth, 

					
			};
			
			final float[] groundColorData = {
					// Front Face (green)
					0.0f, 1.0f, 0.0f, 1.0f,				
					0.0f, 1.0f, 0.0f, 1.0f,
					0.0f, 1.0f, 0.0f, 1.0f,
					0.0f, 1.0f, 0.0f, 1.0f,				
					0.0f, 1.0f, 0.0f, 1.0f,
					0.0f, 1.0f, 0.0f, 1.0f,

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
			
			final float[] groundNormalData = {
					0.0f, 0.0f, 1.0f,				
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
			// Initialize the buffers.
			mColumnPositions = ByteBuffer.allocateDirect(groundPositionData.length * mBytesPerFloat)
					.order(ByteOrder.nativeOrder()).asFloatBuffer();							
			mColumnPositions.put(groundPositionData).position(0);		

			mColumnColors = ByteBuffer.allocateDirect(groundColorData.length * mBytesPerFloat)
					.order(ByteOrder.nativeOrder()).asFloatBuffer();							
			mColumnColors.put(groundColorData).position(0);

			mColumnNormals = ByteBuffer.allocateDirect(groundNormalData.length * mBytesPerFloat)
					.order(ByteOrder.nativeOrder()).asFloatBuffer();							
			mColumnNormals.put(groundNormalData).position(0);
			drawCube();

	 }
	 

	
	
	
	

	private int createAndLinkProgram(final int vertexShaderHandle, final int fragmentShaderHandle, final String[] attributes ) {
		int programHandle = GLES20.glCreateProgram();

		if (programHandle != 0) 
		{
			// Bind the vertex shader to the program.
			GLES20.glAttachShader(programHandle, vertexShaderHandle);			

			// Bind the fragment shader to the program.
			GLES20.glAttachShader(programHandle, fragmentShaderHandle);

			// Bind attributes
			if (attributes != null)
			{
				final int size = attributes.length;
				for (int i = 0; i < size; i++)
				{
					GLES20.glBindAttribLocation(programHandle, i, attributes[i]);
				}						
			}

			// Link the two shaders together into a program.
			GLES20.glLinkProgram(programHandle);

			// Get the link status.
			final int[] linkStatus = new int[1];
			GLES20.glGetProgramiv(programHandle, GLES20.GL_LINK_STATUS, linkStatus, 0);
			if (linkStatus[0] == 0) 
			{				
			//	Log.e(TAG, "Error compiling program: " + GLES20.glGetProgramInfoLog(programHandle));
				GLES20.glDeleteProgram(programHandle);
				programHandle = 0;
			}
		}

		if (programHandle == 0)
		{
			throw new RuntimeException("Error creating program.");
		}

		return programHandle; 

    } 
private int compileShader(final int shaderType, final String shaderSource) {
	int shaderHandle = GLES20.glCreateShader(shaderType);

	if (shaderHandle != 0) 
	{
		// Pass in the shader source.
		GLES20.glShaderSource(shaderHandle, shaderSource);

		// Compile the shader.
		GLES20.glCompileShader(shaderHandle);

		// Get the compilation status.
		final int[] compileStatus = new int[1];
		GLES20.glGetShaderiv(shaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

		// If the compilation failed, delete the shader.
		if (compileStatus[0] == 0) 
		{
			//Log.e(TAG, "Error compiling shader: " + GLES20.glGetShaderInfoLog(shaderHandle));
			GLES20.glDeleteShader(shaderHandle);
			shaderHandle = 0;
		}
	}

	if (shaderHandle == 0)
	{			
		throw new RuntimeException("Error creating shader.");
	}

	return shaderHandle; 

	}
protected String getVertexShader() {
	final String vertexShader =
			
		       "uniform mat4 u_MVPMatrix;      \n"                // A constant representing the combined model/view/projection matrix.
		   + "uniform mat4 u_MVMatrix;       \n"		// A constant representing the combined model/view matrix.	
           + "attribute vec4 a_Position;     \n"                // Per-vertex position information we will pass in.
           + "attribute vec4 a_Color;        \n"                // Per-vertex color information we will pass in.                          
           + "attribute vec3 a_Normal;       \n"		// Per-vertex normal information we will pass in.

           + "varying vec4 v_Color;          \n"                // This will be passed into the fragment shader.
           
           + "void main()                    \n"                // The entry point for our vertex shader.
           + "{                              \n"
           + "   v_Color = a_Color;          \n"                // Pass the color through to the fragment shader. 
                                                                                                   // It will be interpolated across the triangle.
           + "   gl_Position = u_MVPMatrix   \n"         // gl_Position is a special variable used to store the final position.
           + "               * a_Position;   \n"    // Multiply the vertex by the matrix to get the final point in                                                                                              
           + "}                              \n";    // normalized screen coordinates.

	return vertexShader;
} 
protected String getFragmentShader() {
	final String fragmentShader =
			"precision mediump float;       \n"		// Set the default precision to medium. We don't need as high of a 
													// precision in the fragment shader.				
		  + "varying vec4 v_Color;          \n"		// This is the color from the vertex shader interpolated across the 
		  											// triangle per fragment.			  
		  + "void main()                    \n"		// The entry point for our fragment shader.
		  + "{                              \n"
		  + "   gl_FragColor = v_Color;     \n"		// Pass the color directly through the pipeline.		  
		  + "}                              \n";

		return fragmentShader; 

	
}
private void drawFloor(float y1, float dXb) { 
	
	float dX = dXb;
	float Y1 = y1; // is the height of the floor

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
	drawCube(); 

}

private void drawCube() {
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


	

