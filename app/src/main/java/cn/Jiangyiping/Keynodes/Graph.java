package cn.Jiangyiping.Keynodes;

import android.util.Log;

import java.util.Arrays;

public class Graph {
    public static int VertexSize = 11;
    public int []Vertex; //点0-10 11
    public  int [][]Point; //点0-10 11
    public float[][]Matrix;  //数组 两个下标分别存两个点 值存两点距离 Matrix[v][w]=MAX vw两点没连线
    private static final float MAX = Float.MAX_VALUE;

    public void graph(){  //初始化图
       this.Matrix = new float [VertexSize][VertexSize];
        this.Vertex = new int [VertexSize];
        Log.d("GRAPH", "我运行了！");
        this.Point = new  int[VertexSize][VertexSize];
        for(int i=0;i<VertexSize;i++){            //初始化
            this.Vertex[i]=-1; //初始化各个点0-10
            for (int j=0;j<VertexSize;j++)
            {
                if(i==j)
                this.Matrix[i][j]=0;
                else
                    this.Matrix[i][j]=MAX;
                this.Point[i][j]=-1;
            }
        }
    }

    public float getMatrix(int i, int j){
        return Matrix[i][j];
    }

    public void setMatrix(int i , int j , float weight){  //设置边的权值
        Matrix[i][j]=weight;
        Matrix[j][i]=weight;
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
    public  int findrow(int v){
        int row=-1;
        for (int i =0;i<VertexSize;i++)
        {
            for (int j=0;j<VertexSize;j++)
            {
                if (Point[i][j]==v)
                    row=i;
            }
        }
        return row;
    }
    public  int findcol(int v){
         int col=-1;
        for (int i =0;i<VertexSize;i++)
        {
            for (int j=0;j<VertexSize;j++)
            {
                if (Point[i][j]==v)
                    col=i;
            }
        }
        return col;
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

    public boolean HasEdge(int v, int w) //是否已连接
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
    // dijskra
    public int[] DijkstraAlogrim(){
        Log.d("GANS", "运行的点");
        float []dis = new float[11];
        float [][] distant= new float[11][11];
        float[] betweeness = new float[11];
        float []distantsum = new float[11];
        boolean []vis = new boolean [11];
        int []Path=new int[11];
        int []degree = new int[11];
        int[]end=new int[3];
        int [][]num = new int[11][11];
        int []Times=new int[11];int u;
        float []EndAnswer = new float[11];
        end[0]=-1;end[1]=-1;end[2]=-1;
        for(int i =0;i<11;i++)
        {
            for(int j=0;j<11;j++)
            {
                //    Weight[i][j]=MAX;
              //  Matrix[i][j]=MAX;
                distant[i][j]=MAX;
                //Log.d("ANS", Matrix[j][i]+"Matrix的点");
                num[i][j]=0;
            }
            //    Weight[i][i]=0;
           // Matrix[i][i]=0;
            dis[i]=0; //权值 所有i到j
            Path[i]=-1; //记录经过点
            betweeness[i]=-1; //介数记录
            vis[i]=false; //是否已访问
            Times[i]=0; //i在最短路径中经历的次数
            degree[i]=0; //节点度
            distantsum[i]=0; //平均最短路径总和
            EndAnswer[i]=-1; //最终值
          //  distantsum[i]=0;
        }

        for (int j=1;j<VertexSize;j++) //1-10 11
        {
            for (int i=1;i<VertexSize;i++)
            {
                if(Matrix[j][i]!=0&&Matrix[j][i]!=MAX) //有连线
                {
                    //Log.d("ANS", Matrix[j][i]+"Matrix的点");
                    degree[j]++; //度++
                }
            }
            Log.d("ANS", degree[j]+"点的degree");
        }

        for(int j=1;j<VertexSize;j++){
            Arrays.fill(vis, false);
            //Arrays.fill(distantsum, 0);
            Arrays.fill(dis, 0);
            Arrays.fill(Path, j);
            Arrays.fill(Times,0);
            //j是定点 i表示其他点到定点j的最短路径
            for(int i=1;i<VertexSize;i++){
                dis[i] = Matrix[j][i]; //复制距离 此处是j点到所有i的距离
            }
            vis[j]=true;
            for(int x=1;x<VertexSize-1;x++) {  //x是什么鬼
               float min=MAX;int temp=0;
                for(int i=1;i<VertexSize;i++)
                {
                    if(!vis[i]&&dis[i]<min){
                        min=dis[i];
                        temp=i; //最小值
                    }
                }
                Log.d("ANS", temp+"小值");
                vis[temp]=true; //已访问
                for(int i=1;i<VertexSize;i++){
                    if(Matrix[temp][i]+dis[temp]<dis[i]){
                        dis[i]=Matrix[temp][i]+dis[temp];//1到V个点  i到点j的最短距离  更新最短路径
                        Path[i]=temp; //记录路径
                    }
                }

            }
            for(int i=1;i<VertexSize;i++)
            {
                distant[j][i]=dis[i];//j到其他各点的距离
                distantsum[j]=distantsum[j]+distant[j][i];//j点所有最短路径的和
            }
            for(int y=1;y<11;y++){
                if(Path[y]!=j){
                    Times[Path[y]]++;
                    u=Path[y]; //前驱节点
                    while(Path[u]!=j)
                    {
                        Times[Path[u]]++;
                        u=Path[u];//算前前节点。
                        if(Path[u]==j)
                            break;
                    }
                }

            }
            //不同j的分界线
            for(int a=1;a<11;a++)
            {
                num[j][a]=Times[a];//记录不同点j到中路径经过点a的次数
            }
        }




        for(int i=1;i<11;i++){
            int s=0;
            for(int j=1;j<11;j++)
            {
                s=s+num[i][j]; //总和
                betweeness[i]=(float) s/9f; //各个点的介数
            }
            distantsum[i]=distantsum[i]/9;//每个到点i的最短路径之和点数  接近中心性
        }
        for(int y=1;y<VertexSize;y++)
        {
            EndAnswer[y]=0.5f*betweeness[y]+0.1f*degree[y]-0.4f*distantsum[y];
            Log.d("ANS", degree[y]+"度");
            Log.d("ANS", betweeness[y]+"介数");
            Log.d("ANS", distantsum[y]+"平均最短路径");
            Log.d("ANS", EndAnswer[y]+"算出的点");
        }
        float maxanswer=-MAX; int ans=0;
        for(int w=0;w<3;w++)
        {
            for(int z=1;z<VertexSize;z++)
            {
                if(maxanswer<=EndAnswer[z])
                {
                    maxanswer=EndAnswer[z];
                    Log.d("ANS", maxanswer+"算出的点");
                    ans=z;
                }

            }
            EndAnswer[ans]=-MAX; maxanswer=-MAX; //可能的情况是为负数 因此负无穷才能小到底
            end[w]=ans; //取最大的三个点 放炸弹
        }

        //度数怎么计算以及路径判断经过的介数
        return end;
    }


}

