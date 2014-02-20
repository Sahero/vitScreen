/*
 * Author: Sagar Shrestha
 * Created Date: 16 Jan 2014
 * More Options at http://www.jqplot.com/docs/files/jqPlotOptions-txt.html 
 */
/*
function jqPlotExt() {
	//this = chart widget instance
	//this.cfg = options
	this.cfg.grid ={
			borderColor: '#ffffff',
			shadow: false, 
			background: '#ffffff'
	};

}*/

function jqPlotExtBar() {
			//this = chart widget instance
			//this.cfg = options
			this.cfg.grid ={
					borderColor: '#ffffff',
					shadow: false, 
					background: '#ffffff'
			};
			/*this.cfg.axesDefaults = { 
			       //useSeriesColor: true, 
			       tickInterval: 20, 
			       tickOptions: { 
			           formatString: '%d%',
			           showGridline:false	        	  
			       }
		   };*/
			this.cfg.canvasOverlay= {
				      show: true,
				      objects: [
				         {dashedHorizontalLine: {
				            label: 'High',
				            showLabel: true,
				            y: 90,				            
				            lineWidth: 1,
				            color: 'red',
				            shadow: false,
				            fontsize:'10px'
				         },
				         dashedHorizontalLine2: {
					            label: 'Low',
					            showLabel: true,
					            y: 80,					            
					            lineWidth: 1,
					            color: 'green',
					            shadow: false,
					            fontsize:'10px'
					         },
				         dashedHorizontalLine3: {
					            label: 'Min',
					            showLabel: true,
					            y: 60,					            
					            lineWidth: 1,
					            color: 'orange',
					            shadow: false,
					            fontsize:'10px'
					         }
				         }
				      ]   
			};
			this.cfg.seriesDefaults = {
		            
		            renderer:$.jqplot.BarRenderer,
		            rendererOptions: {
		                // Put a 30 pixel margin between bars.
		                barMargin:"250",
		                // Highlight bars when mouse button pressed.
		                // Disables default highlighting on mouse over.
		                highlightMouseDown: true   
		            },
		            pointLabels: {show: true,formatString: '%d%',}	            
		            
		        };
			this.cfg.stackSeries= true;
			this.cfg.legend = {
					renderer: $.jqplot.EnhancedLegendRenderer,
			        show: true,
			        location: 's',
			        placement: 'outsideGrid',
			        rendererOptions: {
                        numberColumns: 2,
                        numberRows : 1
                    }
			};
			 this.cfg.axes= {
			        xaxis: {			        	
			        	min:0.5,
			        	max:1.5,
			        	pad:0,
			        	
			        	tickOptions: { 
			        			showGridline:false ,
			        			showLabel:false,
			        			showMark: false,
					       }
			        },
			        yaxis: {
			        	min:0,
			        	max:100,
			        	tickInterval: 20,
			        	tickOptions: { 
					           formatString: '%d%',
					           showGridline:true	        	  
					       }
			        }
			    };
		}