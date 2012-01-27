#include "frame.hpp"

#include <GL/gl.h>
#include <GL/glu.h>
#include <GL/glut.h>

frame::frame(int argc, char** argv) {
  glutInit(&argc, argv);
  glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGBA | GLUT_DEPTH);
  glutInitWindowSize(500, 500);
  glutInitWindowPosition(0, 0);
  glutCreateWindow(*argv);

  //void(*f)(void) = &frame::render;

  glutDisplayFunc((void(*)(void)) &frame::render);
  glutTimerFunc(1000, (void(*)(int)) &frame::update, 0);
}

void frame::render() {
  glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
  glLoadIdentity();

  double x = 0.0;
  double y = 0.0;
  double zoom = 0.5;
  double SQRT3 = 1.73205081;

  glBegin(GL_POLYGON);
  glColor3f(0.0f, 1.0f, 0.0f);
  glVertex3f(x - 0.5*zoom, y - 0.5*zoom*SQRT3, 0.0f);
  glVertex3f(x + 0.5*zoom, y - 0.5*zoom*SQRT3, 0.0f);
  glVertex3f(x + zoom, y, 0.0f);
  glVertex3f(x + 0.5*zoom, y + 0.5*zoom*SQRT3, 0.0f);
  glVertex3f(x - 0.5*zoom, y + 0.5*zoom*SQRT3, 0.0f);
  glVertex3f(x - zoom, y, 0.0f);
  glEnd();

  glutSwapBuffers();
}

void frame::update(int) {

}

void frame::show() {
  glutMainLoop();
}
