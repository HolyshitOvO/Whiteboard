package com.lai.whiteboard.activities

import android.graphics.BitmapFactory
import android.opengl.GLSurfaceView
import android.opengl.GLSurfaceView.RENDERMODE_WHEN_DIRTY
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.lai.whiteboard.R
import com.lai.whiteboard.ShaderNative
import com.lai.whiteboard.databinding.ActivityTestTextureBinding
import com.lai.whiteboard.util.PointUtils
// import kotlinx.android.synthetic.main.activity_test_texture.*
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


class TestTextureActivity : AppCompatActivity() {

    val bitmaps by lazy {
        BitmapFactory.decodeResource(this.resources, R.mipmap.ic_qxx)
    }

    enum class DRAW_MODEL{
        INIT,DRAW_R
    }


    private var currDraw = DRAW_MODEL.INIT;
    private var look_x:Float =0f
    private var look_y:Float =0f
    private var look_z:Float =0f

    private lateinit var binding: ActivityTestTextureBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_test_texture)
        binding = ActivityTestTextureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.glSurface.setEGLContextClientVersion(3)


        binding.glSurface.setRenderer(object : GLSurfaceView.Renderer {
            override fun onDrawFrame(gl: GL10?) {
                if(currDraw== DRAW_MODEL.INIT){
                    ShaderNative.glTestDraw()
                }else{
                    Log.e("!1111"," x $look_x y $look_y z $look_z")
                    ShaderNative.glTestDrawR(look_x, look_y, look_z)
                }
            }

            override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
                ShaderNative.glInit(width, height, bitmaps)
            }

            override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {

            }
        })


        binding.xSeekBar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                currDraw = DRAW_MODEL.DRAW_R
                look_x = progress.toFloat()
                binding.glSurface.requestRender()

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
        binding.ySeekBar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                currDraw = DRAW_MODEL.DRAW_R
                look_y= progress.toFloat()
                binding.glSurface.requestRender()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
        binding.zSeekBar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                currDraw = DRAW_MODEL.DRAW_R
                look_z =progress.toFloat()
                binding.glSurface.requestRender()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })


        binding.glSurface.renderMode = RENDERMODE_WHEN_DIRTY

        binding.glSurface.setOnTouchListener { v, event ->
            when(event?.action){
                MotionEvent.ACTION_DOWN->{

                }
                MotionEvent.ACTION_MOVE->{
                    val vertexWithPoint = PointUtils.vertexWithPoint(
                        event.x,
                        event.y,
                        binding.glSurface.width,
                        binding.glSurface.height
                    )
                    look_x=vertexWithPoint[0]
                    look_y=vertexWithPoint[1]
                    currDraw = DRAW_MODEL.DRAW_R
                    binding.glSurface

                        .requestRender()
                   // ShaderNative.glTestDrawR(vertexWithPoint[0],vertexWithPoint[1],1f)
                }
            }
            false
        }
    }
}
