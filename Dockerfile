FROM python

COPY requirements.txt req.txt
RUN pip install -r req.txt

COPY exchange.py app.py
EXPOSE 5000
CMD ["python", "app.py"]
