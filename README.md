# PLTWProblem4
A tool to help vizualize the shape of 3d graphs.

# Steps:
```
Get user supplied function
Use JS engine to evaluate function from -2 to 2 (x and y) and return point array
Parse point array in Java
Find max and min values in point array
Scale hue selection based on max and min vals
Iterate through 2d array and plot top view
- setColor(hueOf(f(x,y))
- drawPoint(x,y)
Generate and render contour lines
- only render points that are equal to a certain height
Iterate through 2d array and plot side view
- setColor(hueOf(f(x,y))
- drawPoint(x, f(x,y))
Draw axes and graph labels
```
# Some Cool Graphs
## -1/(x*x+y*y) 
[![name](https://raw.githubusercontent.com/Leek727/PLTWProblem4/main/demo/horn.png?token=GHSAT0AAAAAACBNUZ2CNPMRBMDA5RHWQDCGZDL7H4Q)]()
## x*y*y*y-y*x*x*x
[![name](https://raw.githubusercontent.com/Leek727/PLTWProblem4/main/demo/spin.png?token=GHSAT0AAAAAACBNUZ2DPLTZJRXQ3F3L7C3CZDL7JIQ)]()
