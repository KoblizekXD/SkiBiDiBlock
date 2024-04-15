package lol.koblizek.biblock.model.loader;

import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;
import java.util.List;

public record Face(Style style, List<Entry> faceEntries) implements Instructable {

    public enum Style {
        VERTEX_INDICES(VertexFormats.POSITION), // v1
        VERTEX_TEX_INDICES(VertexFormats.POSITION_TEXTURE), // v1/vt1
        VERTEX_TEX_NORMAL_INDICES(WavefrontModel.POSITION_TEXTURE_NORMAL), // v1/vt1/vn1
        VERTEX_NORMAL_INDICES(WavefrontModel.POSITION_NORMAL); // v1//vn1

        private final VertexFormat vertexFormat;

        Style(VertexFormat format) {
            this.vertexFormat = format;
        }

        public VertexFormat getVertexFormat() {
            return vertexFormat;
        }
    }

    public record Entry(Style superStyle, Integer vertexIndex, Integer texIndex, Integer normalIndex) {
        public Entry {
            if (texIndex < 0) {
                texIndex = -1;
            }
            if (normalIndex < 0) {
                normalIndex = -1;
            }
        }

        public boolean isVertexPresent() {
            return vertexIndex != null;
        }

        public boolean isTexPresent() {
            return texIndex != null;
        }

        public boolean isNormalPresent() {
            return normalIndex != null;
        }
    }

    public static Face fromArgs(String[] args) {
        Style s;
        List<Entry> entries = new ArrayList<>();
        // TODO MAKE IT WORK WITH NEGATIVE VALUES
        if (args[0].contains("//")) {
            s = Style.VERTEX_NORMAL_INDICES;
            for (String arg : args) {
                String[] indices = arg.split("//");
                entries.add(new Entry(s, Integer.parseInt(indices[0]), null, Integer.parseInt(indices[1])));
            }
        } else if (args[0].matches("\\d+/\\d+/\\d+")) {
            s = Style.VERTEX_NORMAL_INDICES;
            for (String arg : args) {
                String[] indices = arg.split("/");
                entries.add(new Entry(s, Integer.parseInt(indices[0]), Integer.parseInt(indices[1]), Integer.parseInt(indices[2])));
            }
        } else if (args[0].matches("\\d+/\\d+")) {
            s = Style.VERTEX_TEX_INDICES;
            for (String arg : args) {
                String[] indices = arg.split("/");
                entries.add(new Entry(s, Integer.parseInt(indices[0]), Integer.parseInt(indices[1]), null));
            }
        } else {
            s = Style.VERTEX_INDICES;
            for (String arg : args) {
                entries.add(new Entry(s, Integer.parseInt(arg), null, null));
            }
        }

        return new Face(s, entries);
    }

    @Override
    public void render(WavefrontModel model, MatrixStack matrixStack) {
        if (faceEntries.size() == 3) {
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder buffer = tessellator.getBuffer();
            buffer.begin(VertexFormat.DrawMode.TRIANGLES, style.vertexFormat);
            for (Entry entry : faceEntries) {
                if (matrixStack == null) {
                    if (entry.isVertexPresent()) {
                        buffer.vertex(model.vertices.get(entry.vertexIndex - 1).x(), model.vertices.get(entry.vertexIndex - 1).y(), model.vertices.get(entry.vertexIndex - 1).z());
                    }
                    if (entry.isTexPresent()) {
                        buffer.texture(model.textures.get(entry.texIndex - 1).x(), model.textures.get(entry.texIndex - 1).y());
                    }
                    if (entry.isNormalPresent()) {
                        buffer.normal(model.normals.get(entry.normalIndex - 1).x(), model.normals.get(entry.normalIndex - 1).y(), model.normals.get(entry.normalIndex - 1).z());
                    }
                } else {
                    if (entry.isVertexPresent()) {
                        buffer.vertex(matrixStack.peek().getPositionMatrix(), model.vertices.get(entry.vertexIndex - 1).x(), model.vertices.get(entry.vertexIndex - 1).y(), model.vertices.get(entry.vertexIndex - 1).z());
                    }
                    if (entry.isTexPresent()) {
                        buffer.texture(model.textures.get(entry.texIndex - 1).x(), model.textures.get(entry.texIndex - 1).y());
                    }
                    if (entry.isNormalPresent()) {
                        buffer.normal(matrixStack.peek().getNormalMatrix(), model.normals.get(entry.normalIndex - 1).x(), model.normals.get(entry.normalIndex - 1).y(), model.normals.get(entry.normalIndex - 1).z());
                    }
                }
                buffer.next();
            }
            tessellator.draw();
        }  else {
            WavefrontModel.LOGGER.warn("Non-triangulated face detected, skipping!");
        }
    }
}
