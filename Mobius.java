import javax.media.j3d.GeometryArray;
import javax.media.j3d.IndexedQuadArray;
import javax.media.j3d.QuadArray;
import javax.media.j3d.Shape3D;
import javax.vecmath.Point3d;
import javax.vecmath.TexCoord2f;
import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;

public class Mobius extends Shape3D {
	public Mobius() {
		int m = 50;
		int n = 50;

		Point3d[] vertices = new Point3d[m * n];
		int index = 0;
		for (int i = 0; i < m; i++) {
			double u = i * 2.0 * Math.PI / (m - 1);
			for (int j = 0; j < n; j++) {
				double v = j * 0.6 / (n - 1) - 0.3;
				double w = v * Math.cos(u / 2) + 1;
				double x = w * Math.cos(u);
				double y = w * Math.sin(u);
				double z = v * Math.sin(u / 2);
				vertices[index++] = new Point3d(x, y, z);
			}
		}

		IndexedQuadArray iqa = new IndexedQuadArray(m * n,
				GeometryArray.COORDINATES | QuadArray.TEXTURE_COORDINATE_2, 8
						* (m - 1) * (n - 1));
		iqa.setCoordinates(0, vertices);

		index = 0;
		for (int i = 0; i < m - 1; i++) {
			for (int j = 0; j < n - 1; j++) {
				iqa.setCoordinateIndex(index++, i * m + j);
				iqa.setCoordinateIndex(index++, (i + 1) * m + j);
				iqa.setCoordinateIndex(index++, (i + 1) * m + j + 1);
				iqa.setCoordinateIndex(index++, i * m + j + 1);
			}
		}

		index = 0;
		// set texture coordinates
		TexCoord2f[] tex = new TexCoord2f[m * n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				tex[index] = new TexCoord2f();
				tex[index] = new TexCoord2f(i * 1f / m, j * 1f / n);
				index++;
			}
		}

		iqa.setCoordinates(0, vertices);
		iqa.setTextureCoordinates(0, 0, tex);
		index = 0;

		// set index for coordinates
		int[] texIndices = new int[8 * (m - 1) * (n - 1)];
		for (int i = 0; i < m - 1; i++) {
			for (int j = 0; j < n - 1; j++) {
				iqa.setCoordinateIndex(index, i * m + j);
				texIndices[index] = i * m + j;
				index++;
				iqa.setCoordinateIndex(index, i * m + j + 1);
				texIndices[index] = i * m + j + 1;
				index++;
				iqa.setCoordinateIndex(index, (i + 1) * m + j + 1);
				texIndices[index] = (i + 1) * m + j + 1;
				index++;
				iqa.setCoordinateIndex(index, (i + 1) * m + j);
				texIndices[index] = (i + 1) * m + j;
				index++;
			}
		}

		iqa.setTextureCoordinateIndices(0, 0, texIndices);

		GeometryInfo gi = new GeometryInfo(iqa);
		NormalGenerator ng = new NormalGenerator();
		ng.generateNormals(gi);

		this.setGeometry(gi.getGeometryArray());
	}
}
