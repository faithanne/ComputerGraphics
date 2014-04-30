/*
 * Faith-Anne Kocadag
 * Spinning Monius Strip
 */

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsConfiguration;
import javax.media.j3d.Alpha;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Material;
import javax.media.j3d.PointLight;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class Project7 extends Applet {

	private BranchGroup createSceneGraph() {
		BranchGroup root = new BranchGroup();
		TransformGroup spin = new TransformGroup();
		spin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		root.addChild(spin);
		// object
		Appearance ap = new Appearance();
		ap.setMaterial(new Material());
		PolygonAttributes pa = new PolygonAttributes();
		pa.setCullFace(PolygonAttributes.CULL_NONE);
		ap.setPolygonAttributes(pa);
		Shape3D shape = new Mobius();
		shape.setAppearance(ap);
		
		// rotating object
		Transform3D tr = new Transform3D();
		tr.setScale(0.25);
		TransformGroup tg = new TransformGroup(tr);
		spin.addChild(tg);
		tg.addChild(shape);
		Alpha alpha = new Alpha(-1, 4000); // function of time for rotation
		RotationInterpolator rotator = new RotationInterpolator(alpha, spin);
		BoundingSphere bounds = new BoundingSphere();
		rotator.setSchedulingBounds(bounds);
		spin.addChild(rotator);
		
		// light and background
		Background background = new Background(1.0f, 1.0f, 1.0f);
	    background.setApplicationBounds(bounds);
	    root.addChild(background);
	    AmbientLight light = new AmbientLight(true, new Color3f(Color.blue));
	    light.setInfluencingBounds(bounds);
	    root.addChild(light);
	    PointLight ptlight = new PointLight(new Color3f(Color.white),
	      new Point3f(3f,3f,3f), new Point3f(1f,0f,0f));
	    ptlight.setInfluencingBounds(bounds);
	    root.addChild(ptlight);
		return root;
	}

	@Override
	public void init() {
		GraphicsConfiguration gc = SimpleUniverse.getPreferredConfiguration();
		Canvas3D cv = new Canvas3D(gc);
		setLayout(new BorderLayout());
		add(cv, BorderLayout.CENTER);
		BranchGroup bg = createSceneGraph();
		bg.compile();
		SimpleUniverse su = new SimpleUniverse(cv);
		su.getViewingPlatform().setNominalViewingTransform();
		su.addBranchGraph(bg);
	}

	public static void main(String[] args) {
		new MainFrame(new Project7(), 800, 600);
	}
}