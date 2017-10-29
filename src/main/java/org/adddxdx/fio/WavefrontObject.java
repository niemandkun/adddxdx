package org.adddxdx.fio;

import org.adddxdx.graphics.Mesh;
import org.adddxdx.math.Vector2;
import org.adddxdx.math.Vector3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WavefrontObject {
    private final static String COMMAND_SEPARATOR = " ";

    private final List<Vector3> mVertices;
    private final List<Vector3> mNormals;
    private final List<Vector2> mUvs;
    private final List<Face> mFaces;

    private WavefrontObject() {
        mVertices = new ArrayList<>();
        mNormals = new ArrayList<>();
        mUvs = new ArrayList<>();
        mFaces = new ArrayList<>();
    }

    public Mesh getMesh() {
        List<Vector3> vertices = new ArrayList<>();
        List<Vector3> normals = new ArrayList<>();
        List<Vector2> uvCoordinates = new ArrayList<>();

        for (Face face : mFaces) {
            if (face.mType != FaceType.TRIANGLE) {
                throw new IllegalStateException("Objects with non triangle faces are not supported yet.");
            }
            for (Vertex vertex : face.mVertices) {
                vertices.add(mVertices.get(vertex.mVertexIndex));
                normals.add(mNormals.get(vertex.mNormalIndex));
                uvCoordinates.add(mUvs.get(vertex.mUvIndex));
            }
        }

        return new Mesh(
                vertices.toArray(new Vector3[vertices.size()]),
                normals.toArray(new Vector3[normals.size()]),
                uvCoordinates.toArray(new Vector2[uvCoordinates.size()])
        );
    }

    private void execute(Command command, String[] args) {
        switch (command) {
            case VERTEX:
                mVertices.add(parseVector3(args));
                break;
            case NORMAL: {
                mNormals.add(parseVector3(args));
            } break;
            case UV: {
                mUvs.add(parseVector2(args));
            } break;
            case FACE: {
                mFaces.add(Face.parse(args));
            } break;
        }
    }

    private Vector3 parseVector3(String[] args) {
        return new Vector3(Float.parseFloat(args[0]), Float.parseFloat(args[1]), Float.parseFloat(args[2]));
    }

    private Vector2 parseVector2(String[] args) {
        return new Vector2(Float.parseFloat(args[0]), 1 - Float.parseFloat(args[1]));
    }

    public static WavefrontObject fromFile(File file) {
        WavefrontObject object = new WavefrontObject();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] args = line.split(COMMAND_SEPARATOR);
                if (args.length == 0) {
                    continue;
                }
                Command command = Command.fromName(args[0]);
                object.execute(command, Arrays.copyOfRange(args, 1, args.length));
            }
        } catch (IOException e) {
            throw new RuntimeException("File `" + file + "` cannot be read: " + e.getMessage());
        }

        return object;
    }

    public enum Command {
        VERTEX("v"),
        NORMAL("vn"),
        UV("vt"),
        FACE("f"),
        USEMTL("usemtl"),
        MTLLIB("mtllib"),
        S("s"),
        OBJECT("o"),
        COMMENT("#");

        private String mName;

        Command(String name) {
            mName = name;
        }

        static Command fromName(String name) {
            for (Command command : values()) {
                if (command.mName.equals(name)) {
                    return command;
                }
            }
            throw new IllegalArgumentException("Do not know about command: " + name);
        }
    }

    public enum FaceType {
        TRIANGLE,
        QUAD,
    }

    private static class Face {
        private FaceType mType;
        private List<Vertex> mVertices;

        private Face(List<Vertex> vertices) {
            mVertices = vertices;
            if (vertices.size() != 3 && vertices.size() != 4) {
                throw new IllegalArgumentException("Invalid count of vertices in face. Expected 3 or 4, got " + vertices.size());
            }
            mType = vertices.size() == 3 ? FaceType.TRIANGLE : FaceType.QUAD;
        }

        static Face parse(String[] args) {
            List<Vertex> vertices = new ArrayList<>();
            for (String vertexDescription : args) {
                vertices.add(Vertex.parse(vertexDescription));
            }
            return new Face(vertices);
        }
    }

    static class Vertex {
        private final static String VERTEX_DESCRIPTION_SEPARATOR = "/";

        private final int mVertexIndex;
        private final int mUvIndex;
        private final int mNormalIndex;

        Vertex(int vertexIndex, int uvIndex, int normalIndex) {
            mVertexIndex = vertexIndex;
            mUvIndex = uvIndex;
            mNormalIndex = normalIndex;
        }

        static Vertex parse(String description) {
            String[] data = description.split(VERTEX_DESCRIPTION_SEPARATOR);
            return new Vertex(parseIndex(data[0]), parseIndex(data[1]), parseIndex(data[2]));
        }

        private static int parseIndex(String index) {
            return Integer.parseInt(index) - 1;
        }
    }
}
