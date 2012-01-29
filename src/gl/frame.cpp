#include "gl/frame.hpp"
#include "util/log.hpp"

#include <GL/gl.h>
#include <GL/glu.h>
#include <GL/glut.h>

bool erki::gl::frame::gl_initialized = false;

erki::gl::frame::frame(std::string title) {

  if (!gl_initialized) {
    LOG_DEBUG("Initializing GLUT.");
    gl_initialized = true;
    int argc = 1;
    char * argv = "Titel";
    glutInit(&argc, &argv);
    glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGBA | GLUT_DEPTH);
    glutInitWindowSize(640, 480);
    glutInitWindowPosition(0, 0);
  }

  LOG_DEBUG("Creating new frame with title “" << title << "”.");
  glutCreateWindow(title.c_str());

  glutDisplayFunc((void(*)(void)) &frame::render);
  glutIdleFunc((void(*)(void)) &frame::update);
}

void erki::gl::frame::render() {
  LOG_DEBUG("Render called.");
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

void erki::gl::frame::update() {
}

void erki::gl::frame::show() {
  LOG_DEBUG("Show called.");
  glutMainLoop();
}
