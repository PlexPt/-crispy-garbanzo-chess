package cn.Jiangyiping.Keynodes;

public class Graph {
    public static int VertexSize = 11;
    public int []Vertex; //点0-10 11
    public  int [][]Point; //点0-10 11
    public float[][]Matrix;  //数组 两个下标分别存两个点 值存两点距离 Matrix[v][w]=MAX vw两点没连线
    private static final float MAX = Float.MAX_VALUE;
    public void graph(){  //初始化图
        Matrix = new float [VertexSize][VertexSize];
        Vertex = new int [VertexSize];
        Point = new  int[VertexSize][VertexSize];
        for(int i=0;i<VertexSize;i++){            //初始化
            Vertex[i]=i; //初始化各个点0-10
            for (int j=0;j<VertexSize;j++)
            {
                if(i==j)
                Matrix[i][j]=0;
                else
                    Matrix[i][j]=MAX;
                Point[i][j]=-1;
            }
        }
    }
    public float getMatrix(int i, int j){
        return Matrix[i][j];
    }

    public void setMatrix(int i , int j , float weight){  //设置边的权值
        Matrix[i][j]=weight;
    } //初始化
    //public  void setVertex(int j )
    public int getVertex(int i){
        return Vertex[i];
    }
    public int lookVertex(int row,int col){
        int x = 0;
        for(int i=0;i<11;i++)
        {
            for(int j=0;j<11;j++)
            {
                if(i==row&&col==j)
                     x = Point[i][j];  //point[row][col]的值是vertex的标号
            }
        }
        return x;
    }
    public void setVertex(int i){
        Vertex[i]=1;
    }
    public void deleteVertex(int i,int[]Vertex){
        Vertex[i]=-1;
    }  //现在没有用，思路是炸弹炸开可以调用删掉范围内的点和线
    public void deleteEdge(int i,int j,float[][]Matrix){
        Matrix[i][j]=MAX;
    } //炸线 删掉线

    boolean HasEdge(int v, int w) //是否已连接
    {
        if (Matrix[v][w]==MAX||Matrix[v][w]==1)
            return  false;
        else
            return true;
    }
    public  float getWeight(int StartX,int StartY,int EndX,int EndY){  //算权值
        float weight = 0f;
       weight =(float) Math.sqrt((StartX-EndX)*(StartX-EndX)+(StartY-EndY)*(StartY-EndY));
        return weight;
    }
	/*public int Vertex(int v);
	public int Edge(int e); //边
	public void addEdge(int v,int w); //加边

	void show();
	public Iterable<Integer>adj(int v);
	*/
}
