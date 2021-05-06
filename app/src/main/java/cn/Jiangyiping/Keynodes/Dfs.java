package cn.Jiangyiping.Keynodes;
//连通片的算法 还要调试 可以暂时先不管
public class Dfs {
  /* Graph G = new Graph();
    private int Count=0; //记录连通的点数
    private int[]id; //每个节点所对应的联通分量标记
    private boolean[] Visited; //记录是否已访问

    void Initial(){
        Count=0;
        for(int i =0;i<11;i++){
            Visited[i]=false; //初始化
        }
    }
    void dfs(int v){ //深度优先遍历
        //Visited[v]=true;
        for(int j=1;j<11;j++){
            if(!Visited[j]&&G.HasEdge(v,j)){
                Visited[v]=true; //标记已访问
                Count++;  //记录连通分量
                dfs(j); //递归
            }
        }
		*//*Visited[v]=true; //维护联通分量
		id[v]=ccount;
		for(int i:G.adj(v)){//遍历所有元素
			if(!Visited[i]){
				dfs(i);
			}
		}*//*
    }
    *//*public ccdfs(Graph graph){
        G = graph;
        Visited = new boolean[G.Vertex()];
        id = new int[G.Vertex()];
        ccount = 0;
        for(int i=0;i<G.Vertex();i++){
            Visited[i] = false;
            id[i]=-1;
        }
        for(int i=0;i<G.Vertex();i++){
            if(!Visited[i]){
                dfs(i);
                ccount++;
            }
        }
        }*//*
    int Count(){
        return Count; //返回连通片大小
    }*/
}
