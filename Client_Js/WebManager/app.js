import createError from 'http-errors';
import express from 'express';
import path from 'path';
import cookieParser from 'cookie-parser';
import logger from 'morgan';
import { fileURLToPath } from 'url';
import * as fs from 'fs';

import {indexRouter} from './routes/index.js';
import {partieRouter} from './routes/partie.js';
import {jeuRouter} from './routes/jeu.js';

var app = express();

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'pug');

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.use('/', indexRouter);
app.use('/partie', partieRouter);
app.use('/jeu', jeuRouter);
app.use('/images', express.static(path.join(__dirname, 'public/images')));

app.get('/api/svg-files', (req, res) => {

  const dossierImages = path.join(__dirname, 'public', 'images', 'tiles', 'specials');
  
  fs.readdir(dossierImages, (err, fichiers) => {
    if (err) {
      console.error('Erreur lors de la lecture du dossier:', err);
      return res.status(500).json({ error: 'Erreur lors de la lecture du dossier' });
    }
    const svgFiles = fichiers.filter(fichier => 
      path.extname(fichier).toLowerCase() === '.svg'
    );
    res.json(svgFiles);
  });
});

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  next(createError(404));
});


app.use(function(err, req, res, next) {
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};
  res.status(err.status || 500);
  res.render('error');
});

export {app};
